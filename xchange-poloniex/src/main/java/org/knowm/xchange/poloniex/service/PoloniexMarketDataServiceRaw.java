package org.knowm.xchange.poloniex.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.PoloniexErrorAdapter;
import org.knowm.xchange.poloniex.PoloniexUtils;
import org.knowm.xchange.poloniex.dto.PoloniexException;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexCurrencyInfo;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexDepth;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexMarketData;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexPublicTrade;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexTicker;

public class PoloniexMarketDataServiceRaw extends PoloniexBaseService {

  private final long cache_delay = 1000L;
  private HashMap<String, PoloniexMarketData> TickermarketData;
  private long next_refresh = System.currentTimeMillis() + cache_delay;

  // There is no point to query the ticker instantly again when
  // all tickers are returned on 1 query available in the hash map
  // let's wait a seconds and save our self a call for each ticker in our calling for loop.

  /**
   * Constructor
   *
   * @param exchange Exchange instance for the service
   */
  public PoloniexMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public ArrayList<ArrayList<Object>> getCandlestickChartData(CurrencyPair currencyPair, long start, long end, String period, int limit)
      throws IOException {
    try {
      return poloniex.getHistoryCandles(
          PoloniexUtils.toPairString(currencyPair), start, end, period, limit);
    } catch (PoloniexException e) {
      throw PoloniexErrorAdapter.adapt(e);
    }
  }
}
