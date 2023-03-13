package org.knowm.xchange.cryptodotcom;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cryptodotcom.service.CryptodotcomMarketDataService;


/** @author jamespedwards42 */
public class CryptodotcomExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new CryptodotcomMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    final ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.crypto.com/");
    exchangeSpecification.setHost("api.crypto.com");
    exchangeSpecification.setExchangeName("Crypto.com");
    exchangeSpecification.setExchangeDescription(
        "Founded in July of 2016, Cryto.com is a bitcoin wallet and platform where merchants and consumers can transact with the new digital currency bitcoin.");
    return exchangeSpecification;
  }
}
