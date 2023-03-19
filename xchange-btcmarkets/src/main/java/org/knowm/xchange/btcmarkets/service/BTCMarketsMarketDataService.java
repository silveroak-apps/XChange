package org.knowm.xchange.btcmarkets.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcmarkets.BTCMarketsAdapters;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsTicker;
import org.knowm.xchange.btcmarkets.dto.v3.marketdata.BTCCandleStick;
import org.knowm.xchange.btcmarkets.dto.v3.marketdata.BTCMarketsKlineInterval;
import org.knowm.xchange.btcmarkets.dto.v3.marketdata.BTCMarketsMarketTradeParams;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParamWithLimit;

/** @author Matija Mazi (with additions from CDP) */
public class BTCMarketsMarketDataService extends BTCMarketsMarketDataServiceRaw
    implements MarketDataService {

  public BTCMarketsMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    BTCMarketsTicker t = getBTCMarketsTicker(currencyPair);
    return BTCMarketsAdapters.adaptTicker(currencyPair, t);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return BTCMarketsAdapters.adaptOrderBook(getBTCMarketsOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    return BTCMarketsAdapters.adaptMarketTrades(getBTCMarketsTrade(currencyPair), currencyPair);
  }

  /** @param params use {@link BTCMarketsMarketTradeParams} for params */
  @Override
  public Trades getTrades(Params params) throws IOException {

    return BTCMarketsAdapters.adaptMarketTrades(
        getBTCMarketsTrade(((BTCMarketsMarketTradeParams) params).currencyPair, params),
        ((BTCMarketsMarketTradeParams) params).currencyPair);
  }

  @Override
  public CandleStickData getCandleStickData(CurrencyPair currencyPair, CandleStickDataParams params) throws IOException {
    if (!(params instanceof DefaultCandleStickParam)) {
      throw new NotYetImplementedForExchangeException("Only DefaultCandleStickParam is supported");
    }
    DefaultCandleStickParam defaultCandleStickParam = (DefaultCandleStickParam) params;
    BTCMarketsKlineInterval periodType =
            BTCMarketsKlineInterval.getPeriodTypeFromSecs(defaultCandleStickParam.getPeriodInSecs());
    if (periodType == null) {
      throw new NotYetImplementedForExchangeException("Only discrete period values are supported;" +
              Arrays.toString(BTCMarketsKlineInterval.getSupportedPeriodsInSecs()));
    }

    int limit = 5;
    if (params instanceof DefaultCandleStickParamWithLimit) {
      limit = ((DefaultCandleStickParamWithLimit) params).getLimit();
    }

    List<BTCCandleStick> historyCandle = getCandleSticks(currencyPair, periodType.code(),
            limit);
    return BTCMarketsAdapters.adaptCandleStickData(historyCandle, currencyPair, periodType);
  }
}
