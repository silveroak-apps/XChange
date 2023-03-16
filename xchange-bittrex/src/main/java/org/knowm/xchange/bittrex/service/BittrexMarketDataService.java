package org.knowm.xchange.bittrex.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import lombok.SneakyThrows;
import org.knowm.xchange.bittrex.BittrexAdapters;
import org.knowm.xchange.bittrex.BittrexAuthenticated;
import org.knowm.xchange.bittrex.BittrexErrorAdapter;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.bittrex.BittrexUtils;
import org.knowm.xchange.bittrex.dto.BittrexException;
import org.knowm.xchange.bittrex.dto.marketdata.*;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam;
import org.knowm.xchange.service.marketdata.params.Params;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParamWithLimit;

/**
 * Implementation of the market data service for Bittrex
 *
 * <ul>
 *   <li>Provides access to various market data values
 * </ul>
 */
public class BittrexMarketDataService extends BittrexMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexMarketDataService(
      BittrexExchange exchange,
      BittrexAuthenticated bittrex,
      ResilienceRegistries resilienceRegistries) {
    super(exchange, bittrex, resilienceRegistries);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    try {
      String marketSymbol = BittrexUtils.toPairString(currencyPair);
      // The only way is to make two API calls since the essential information is split between
      // market
      // summary and ticker calls...
      BittrexMarketSummary bittrexMarketSummary =
          bittrexAuthenticated.getMarketSummary(marketSymbol);
      BittrexTicker bittrexTicker = bittrexAuthenticated.getTicker(marketSymbol);
      return BittrexAdapters.adaptTicker(bittrexMarketSummary, bittrexTicker);
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    try {
      Set<CurrencyPair> currencyPairs = Collections.EMPTY_SET;
      if (params instanceof CurrencyPairsParam) {
        currencyPairs = new HashSet<>(((CurrencyPairsParam) params).getCurrencyPairs());
      }

      // The only way is to make two API calls since the essential information is split between
      // market
      // summary and ticker calls...
      List<BittrexMarketSummary> bittrexMarketSummaries = getBittrexMarketSummaries();
      List<BittrexTicker> bittrexTickers = getBittrexTickers();
      return BittrexAdapters.adaptTickers(currencyPairs, bittrexMarketSummaries, bittrexTickers);
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    try {
      int depth = 500;

      if (args != null && args.length > 0) {
        if (args[0] instanceof Integer && (Integer) args[0] > 0 && (Integer) args[0] < 500) {
          depth = (Integer) args[0];
        }
      }
      return getBittrexSequencedOrderBook(BittrexUtils.toPairString(currencyPair), depth)
          .getOrderBook();
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    try {
      List<BittrexTrade> trades = getBittrexTrades(BittrexUtils.toPairString(currencyPair));
      return BittrexAdapters.adaptTrades(trades, currencyPair);
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }

  @SneakyThrows
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

    List<BittrexCandle> bittrexKlines = getBittrexCandles ( BittrexAdapters.adaptToSymbolFromCurrencyPair(currencyPair) ,
            periodType.code());
    return BittrexAdapters.adaptBitstampCandleStickData(bittrexKlines, currencyPair, periodType);

  }
}
