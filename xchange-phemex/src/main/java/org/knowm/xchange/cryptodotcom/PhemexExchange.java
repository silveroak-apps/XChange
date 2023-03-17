package org.knowm.xchange.cryptodotcom;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cryptodotcom.service.PhemexMarketDataService;


/** @author jamespedwards42 */
public class PhemexExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new PhemexMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    final ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.phemex.com/");
    exchangeSpecification.setHost("api.phemex.com");
    exchangeSpecification.setExchangeName("Phemex Exchange");
    exchangeSpecification.setExchangeDescription(
        "Phemex is a bitcoin wallet and platform where merchants and consumers can transact with the new digital currency bitcoin.");
    return exchangeSpecification;
  }
}
