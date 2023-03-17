package org.knowm.xchange.cryptodotcom.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.cryptodotcom.Phemex;

import java.io.IOException;
import java.util.LinkedHashMap;

/** @author jamespedwards42 */
class PhemexMarketDataServiceRaw {

  protected final Phemex phemex;
  /**
   * Constructor
   *
   * @param exchange
   */
  public PhemexMarketDataServiceRaw(Exchange exchange) {
    phemex =
            ExchangeRestProxyBuilder.forInterface(
                            Phemex.class, exchange.getExchangeSpecification())
                    .build();
  }


  /**
   * Unauthenticated resource that displays the current Historical spot rates for Bitcoin in USD.
   *
   * @return With candles based on the parameters
   * @throws IOException
   * @see <a
   *     href="https://coinbase.com/api/doc/1.0/prices/exchange_rates.html">coinbase.com/api/doc/1.0/prices/exchange_rates.html</a>
   */
  public LinkedHashMap getCoinbaseHistoricalCandles(String productId,Long start, Long from, Integer timeframe) throws IOException {

    return phemex.getHistoricalCandles(productId,  start, from, timeframe);
  }
}
