package org.knowm.xchange.coinbase.service.marketdata;

import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;

import org.knowm.xchange.coinbase.CoinbaseExchange;
import org.knowm.xchange.coinbase.service.CoinbaseMarketDataService;

import org.knowm.xchange.service.marketdata.MarketDataService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/** @author timmolter */
public class MarketDataServiceIntegration {

  static Exchange exchange;
  static MarketDataService marketDataService;

  @BeforeClass
  public static void beforeClass() {
    exchange = ExchangeFactory.INSTANCE.createExchange(CoinbaseExchange.class);
    marketDataService = exchange.getMarketDataService();
  }

  @Test
  public void getHistoricalPrice() throws Exception {

    CoinbaseMarketDataService coinbaseService = (CoinbaseMarketDataService) marketDataService;
    List<ArrayList<Object>> candle = coinbaseService.getCoinbaseHistoricalCandles("BTC-USDT",
            3600,
            new Date(1678500000000L).getTime(),
            new Date().getTime());
    assertThat(candle).isNotNull();
  }

}
