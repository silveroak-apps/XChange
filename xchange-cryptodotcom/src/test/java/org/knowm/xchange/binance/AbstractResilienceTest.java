package org.knowm.xchange.cryptodotcom;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;

public class AbstractResilienceTest {

  @Rule public WireMockRule wireMockRule = new WireMockRule();

  public static int READ_TIMEOUT_MS = 1000;

  @Before
  public void resertResilienceRegistries() {
    cryptodotcomExchange.resetResilienceRegistries();
  }

  protected cryptodotcomExchange createExchangeWithRetryEnabled() {
    return createExchange(true, false);
  }

  protected cryptodotcomExchange createExchangeWithRetryDisabled() {
    return createExchange(false, false);
  }

  protected cryptodotcomExchange createExchangeWithRateLimiterEnabled() {
    return createExchange(false, true);
  }

  protected cryptodotcomExchange createExchange(boolean retryEnabled, boolean rateLimiterEnabled) {
    cryptodotcomExchange exchange =
        (cryptodotcomExchange)
            ExchangeFactory.INSTANCE.createExchangeWithoutSpecification(cryptodotcomExchange.class);
    ExchangeSpecification specification = exchange.getDefaultExchangeSpecification();
    specification.setHost("localhost");
    specification.setSslUri("http://localhost:" + wireMockRule.port() + "/");
    specification.setPort(wireMockRule.port());
    specification.setShouldLoadRemoteMetaData(false);
    specification.setHttpReadTimeout(READ_TIMEOUT_MS);
    specification.getResilience().setRetryEnabled(retryEnabled);
    specification.getResilience().setRateLimiterEnabled(rateLimiterEnabled);
    exchange.applySpecification(specification);
    return exchange;
  }
}
