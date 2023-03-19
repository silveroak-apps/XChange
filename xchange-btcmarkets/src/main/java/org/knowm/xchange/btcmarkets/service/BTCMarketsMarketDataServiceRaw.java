package org.knowm.xchange.btcmarkets.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsOrderBook;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsTicker;
import org.knowm.xchange.btcmarkets.dto.v3.marketdata.BTCCandleStick;
import org.knowm.xchange.btcmarkets.dto.v3.marketdata.BTCMarketsMarketTradeParams;
import org.knowm.xchange.btcmarkets.dto.v3.marketdata.BTCMarketsTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.marketdata.params.Params;

import static org.knowm.xchange.btcmarkets.BTCUtils.toDateAsISO8601;

public class BTCMarketsMarketDataServiceRaw extends BTCMarketsBaseService {

  public BTCMarketsMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BTCMarketsTicker getBTCMarketsTicker(CurrencyPair currencyPair) throws IOException {
    return btcmPublic.getTicker(
        currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
  }

  public BTCMarketsOrderBook getBTCMarketsOrderBook(CurrencyPair currencyPair) throws IOException {
    return btcmPublic.getOrderBook(
        currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
  }

  public List<BTCMarketsTrade> getBTCMarketsTrade(CurrencyPair currencyPair) throws IOException {
    return btcmPublic.getTrades(
        currencyPair.base.getCurrencyCode() + "-" + currencyPair.counter.getCurrencyCode());
  }

  public List<BTCMarketsTrade> getBTCMarketsTrade(CurrencyPair currencyPair, Params parameters)
      throws IOException {
    return btcmPublic.getTrades(
        ((BTCMarketsMarketTradeParams) parameters).before,
        ((BTCMarketsMarketTradeParams) parameters).after,
        ((BTCMarketsMarketTradeParams) parameters).limit,
        currencyPair.base.getCurrencyCode() + "-" + currencyPair.counter.getCurrencyCode());
  }

  public List<BTCCandleStick> getCandleSticks(CurrencyPair currencyPair, String timeWindow, int limit) throws IOException {

    return btcmPublic.getCandles(currencyPair.base.getCurrencyCode() + "-" + currencyPair.counter.getCurrencyCode(),
            timeWindow, limit);
  }
}
