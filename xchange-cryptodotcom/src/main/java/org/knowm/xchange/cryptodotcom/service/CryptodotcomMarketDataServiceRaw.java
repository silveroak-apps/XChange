package org.knowm.xchange.cryptodotcom.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.cryptodotcom.Cryptodotcom;
import org.knowm.xchange.cryptodotcom.dto.KlineCandleData;

import java.io.IOException;
import java.util.LinkedHashMap;

/** @author jamespedwards42 */
class CryptodotcomMarketDataServiceRaw {

  protected final Cryptodotcom cryptodotcom;
  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptodotcomMarketDataServiceRaw(Exchange exchange) {
    cryptodotcom =
            ExchangeRestProxyBuilder.forInterface(
                            Cryptodotcom.class, exchange.getExchangeSpecification())
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
  public LinkedHashMap getCoinbaseHistoricalCandles(String productId, String timeframe) throws IOException {

    return cryptodotcom.getHistoricalCandles(productId,  timeframe);
  }
}
