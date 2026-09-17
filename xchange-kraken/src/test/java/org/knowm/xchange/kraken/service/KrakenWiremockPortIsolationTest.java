package org.knowm.xchange.kraken.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.ExternalResource;

/**
 * Regression guard for the WireMock port collision that broke the Kraken suite on the shared
 * self-hosted CI runner.
 *
 * <p>The runner already listens on 8080 (the deployed price synchroniser serves its /healtz endpoint
 * there), so every WireMock-backed Kraken test died at startup with a {@code BindException} when the
 * harness used WireMock's fixed default port. The harness must let the OS choose an ephemeral port
 * instead, so it works regardless of what the host already has bound.
 */
public class KrakenWiremockPortIsolationTest extends BaseWiremockTest {

  private static final int WIREMOCK_DEFAULT_PORT = 8080;

  private static ServerSocket defaultPortBlocker;

  /**
   * Runs before the class body and before any test instance (and therefore before
   * {@link BaseWiremockTest}'s {@code @Rule} starts), holding WireMock's default port so a
   * regression to a fixed port cannot start.
   */
  @ClassRule
  public static final ExternalResource occupyWireMockDefaultPort =
      new ExternalResource() {
        @Override
        protected void before() {
          try {
            ServerSocket socket = new ServerSocket();
            socket.setReuseAddress(false);
            socket.bind(new InetSocketAddress("127.0.0.1", WIREMOCK_DEFAULT_PORT));
            defaultPortBlocker = socket;
          } catch (IOException defaultPortAlreadyTaken) {
            // The host already holds 8080: the exact condition this test guards against. The rule
            // must still not depend on that port, so continue with the external holder in place.
            defaultPortBlocker = null;
          }
        }

        @Override
        protected void after() {
          if (defaultPortBlocker != null) {
            try {
              defaultPortBlocker.close();
            } catch (IOException ignored) {
              // Nothing useful to do while releasing the port for the next run.
            }
            defaultPortBlocker = null;
          }
        }
      };

  @Test
  public void baseRuleLeavesPortSelectionToTheOs() {
    assertThat(wireMockRule.getOptions().portNumber())
        .as(
            "Kraken's WireMock harness must request an ephemeral port, not the fixed default %s",
            WIREMOCK_DEFAULT_PORT)
        .isZero();
  }

  @Test
  public void ruleStartsOnAnEphemeralPortWhenTheDefaultPortIsBusy() {
    assertThat(wireMockRule.isRunning()).isTrue();
    assertThat(wireMockRule.port()).isNotEqualTo(WIREMOCK_DEFAULT_PORT);
  }
}
