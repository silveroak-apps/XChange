package org.knowm.xchange.cryptodotcom.us;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cryptodotcom.cryptodotcomUsExchange;
import org.knowm.xchange.cryptodotcom.dto.meta.cryptodotcomSystemStatus;
import org.knowm.xchange.cryptodotcom.service.cryptodotcomUsAccountService;

public class cryptodotcomUsExchangeIntegration {
  protected static cryptodotcomUsExchange exchange;

  @BeforeClass
  public static void beforeClass() throws Exception {
    createExchange();
  }

  @Test
  public void testSetupIsCorrect() {
    ExchangeSpecification specification = exchange.getDefaultExchangeSpecification();
    assertThat(specification.getExchangeName().equalsIgnoreCase("cryptodotcom US")).isTrue();
    assertThat(specification.getSslUri().equalsIgnoreCase("https://api.cryptodotcom.us")).isTrue();
    assertThat(specification.getHost().equalsIgnoreCase("www.cryptodotcom.us")).isTrue();
    assertThat(specification.getExchangeDescription().equalsIgnoreCase("cryptodotcom US Exchange."))
        .isTrue();
    assertThat(specification.getExchangeClass().equals(cryptodotcomUsExchange.class)).isTrue();
    assertThat(specification.getResilience()).isNotNull();
  }

  @Test
  public void testSystemStatus() throws IOException {
    assumeProduction();
    cryptodotcomSystemStatus systemStatus =
        ((cryptodotcomUsAccountService) exchange.getAccountService()).getSystemStatus();
    assertThat(systemStatus).isNotNull();
    // Not yet supported by cryptodotcom.us
    assertThat(systemStatus.getStatus()).isNull();
  }

  protected static void createExchange() throws Exception {
    exchange = ExchangeFactory.INSTANCE.createExchangeWithoutSpecification(cryptodotcomUsExchange.class);
    ExchangeSpecification spec = exchange.getDefaultExchangeSpecification();
    boolean useSandbox =
        Boolean.parseBoolean(
            System.getProperty(Exchange.USE_SANDBOX, Boolean.FALSE.toString()));
    spec.setExchangeSpecificParametersItem(Exchange.USE_SANDBOX, useSandbox);
    exchange.applySpecification(spec);
  }

  protected void assumeProduction() {
    Assume.assumeFalse("Using sandbox", exchange.usingSandbox());
  }
}
