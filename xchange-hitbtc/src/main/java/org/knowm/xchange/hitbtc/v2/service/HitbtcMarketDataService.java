package org.knowm.xchange.hitbtc.v2.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.hitbtc.v2.HitbtcAdapters;
import org.knowm.xchange.hitbtc.v2.HitbtcUtil;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcCandle;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcKlineInterval;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSort;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTrade;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParamWithLimit;

public class HitbtcMarketDataService extends HitbtcMarketDataServiceRaw
    implements MarketDataService {

  public HitbtcMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return HitbtcAdapters.adaptTicker(getHitbtcTicker(currencyPair), currencyPair);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    return HitbtcAdapters.adaptTickers(getHitbtcTickers());
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    if (args == null || args.length == 0) {
      return HitbtcAdapters.adaptOrderBook(getHitbtcOrderBook(currencyPair), currencyPair);
    } else {
      Integer limit = (Integer) args[0];
      return HitbtcAdapters.adaptOrderBook(getHitbtcOrderBook(currencyPair, limit), currencyPair);
    }
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    if (args == null || args.length == 0) {
      return HitbtcAdapters.adaptTrades(getHitbtcTrades(currencyPair), currencyPair);
    }

    long from = (Long) args[0]; // <trade_id> or <timestamp>
    HitbtcTrade.HitbtcTradesSortField sortBy =
        (HitbtcTrade.HitbtcTradesSortField) args[1]; // "trade_id" or "timestamp"
    HitbtcSort sortDirection = (HitbtcSort) args[2]; // "asc" or "desc"
    long startIndex = (Long) args[3]; // 0
    long max_results = (Long) args[4]; // max is 1000
    long offset = (Long) args[5]; // max is 100000

    return HitbtcAdapters.adaptTrades(
        getHitbtcTrades(currencyPair, from, sortBy, sortDirection, startIndex, max_results, offset),
        currencyPair);
  }

  public Trades getTradesCustom(
      CurrencyPair currencyPair,
      long fromTradeId,
      HitbtcTrade.HitbtcTradesSortField sortBy,
      HitbtcSort sortDirection,
      long limit)
      throws IOException {

    return HitbtcAdapters.adaptTrades(
        getHitbtcTrades(currencyPair, fromTradeId, sortBy, sortDirection, limit), currencyPair);
  }

  @Override
  public CandleStickData getCandleStickData(CurrencyPair currencyPair, CandleStickDataParams params) throws IOException {
    if (!(params instanceof DefaultCandleStickParam)) {
      throw new NotYetImplementedForExchangeException("Only DefaultCandleStickParam is supported");
    }
    DefaultCandleStickParam defaultCandleStickParam = (DefaultCandleStickParam) params;
    HitbtcKlineInterval periodType =
            HitbtcKlineInterval.getPeriodTypeFromSecs(defaultCandleStickParam.getPeriodInSecs());
    if (periodType == null) {
      throw new NotYetImplementedForExchangeException("Only discrete period values are supported;" +
              Arrays.toString(HitbtcKlineInterval.getSupportedPeriodsInSecs()));
    }

    int limit = 5;
    if (params instanceof DefaultCandleStickParamWithLimit) {
      limit = ((DefaultCandleStickParamWithLimit) params).getLimit();
    }
    String startDate = HitbtcUtil.toDateAsISO8601(defaultCandleStickParam.getStartDate().getTime());
    String endDate = HitbtcUtil.toDateAsISO8601(defaultCandleStickParam.getEndDate().getTime());
    List<HitbtcCandle> candles =
            getHitbtcCandles(currencyPair,  limit, periodType.code(), startDate, endDate, "ASC");

    return HitbtcAdapters.adaptCandleStickData(candles, currencyPair, periodType);
  }
}
