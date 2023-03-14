package org.knowm.xchange.bitstamp.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.BitstampAdapters;
import org.knowm.xchange.bitstamp.dto.marketdata.KlineInterval;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParamWithLimit;

/** @author Matija Mazi */
public class BitstampMarketDataService extends BitstampMarketDataServiceRaw
    implements MarketDataService {

  public BitstampMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return BitstampAdapters.adaptTicker(getBitstampTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return BitstampAdapters.adaptOrderBook(getBitstampOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    BitstampTime time = args.length > 0 ? (BitstampTime) args[0] : null;
    return BitstampAdapters.adaptTrades(getTransactions(currencyPair, time), currencyPair);
  }

  @Override
  public CandleStickData getCandleStickData(CurrencyPair currencyPair, CandleStickDataParams params) throws IOException {
    if (!(params instanceof DefaultCandleStickParam)) {
      throw new NotYetImplementedForExchangeException("Only DefaultCandleStickParam is supported");
    }
    DefaultCandleStickParam defaultCandleStickParam = (DefaultCandleStickParam) params;
    KlineInterval periodType =
            KlineInterval.getPeriodTypeFromSecs(defaultCandleStickParam.getPeriodInSecs());
    if (periodType == null) {
      throw new NotYetImplementedForExchangeException("Only discrete period values are supported;" +
              Arrays.toString(KlineInterval.getSupportedPeriodsInSecs()));
    }

    Integer limit = null;
    if (params instanceof DefaultCandleStickParamWithLimit) {
      limit = ((DefaultCandleStickParamWithLimit) params).getLimit();
    }

    LinkedHashMap bitstampKlines = getOhlcData ( currencyPair ,
            defaultCandleStickParam.getStartDate().getTime(), defaultCandleStickParam.getEndDate().getTime(), periodType.code(), limit);
    return BitstampAdapters.adaptBitstampCandleStickData(bitstampKlines, currencyPair, periodType);
  }
}
