package org.knowm.xchange.gateio.v4;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.gateio.v4.service.GateioMarketDataService;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;

public class GateioExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new GateioMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.gateio.ws");
    exchangeSpecification.setHost("api.gateio.ws");
    exchangeSpecification.setExchangeName("Gateio");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    throw new ExchangeException("Gate.io does not require a nonce factory.");
  }

}
