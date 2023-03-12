package org.knowm.xchange.coinbase.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbase.CoinbaseAdapters;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbasePrice;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseSpotPriceHistory;
import org.knowm.xchange.coinbase.dto.marketdata.KlineInterval;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParamWithLimit;

/** @author jamespedwards42 */
public class CoinbaseMarketDataService extends CoinbaseMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinbaseMarketDataService(Exchange exchange) {

    super(exchange);
  }

  /**
   * @param args Optional Boolean. If true an additional call to retrieve the spot price history
   *     will be made and used to populate the 24 hour high and low values for the Ticker.
   * @return A Ticker with Coinbase's current buy price as the best ask, sell price as the best bid,
   *     spot price as the last value, and can optionally use the spot price history to find the 24
   *     hour high and low.
   */
  @Override
  public Ticker getTicker(CurrencyPair currencyPair, final Object... args) throws IOException {

    final String currency = currencyPair.counter.getCurrencyCode();
    final CoinbasePrice buyPrice = super.getCoinbaseBuyPrice(BigDecimal.ONE, currency);
    final CoinbasePrice sellPrice = super.getCoinbaseSellPrice(BigDecimal.ONE, currency);
    final CoinbaseMoney spotRate = super.getCoinbaseSpotRate(currency);

    final CoinbaseSpotPriceHistory coinbaseSpotPriceHistory =
        (args != null
                && args.length > 0
                && args[0] != null
                && args[0] instanceof Boolean
                && (Boolean) args[0])
            ? super.getCoinbaseHistoricalSpotRates()
            : null;

    return CoinbaseAdapters.adaptTicker(
        currencyPair, buyPrice, sellPrice, spotRate, coinbaseSpotPriceHistory);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, final Object... args) {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, final Object... args) {

    throw new NotAvailableFromExchangeException();
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

    List<ArrayList<Object>> klines = getCoinbaseHistoricalCandles( CoinbaseAdapters.convertToCoinbaseSymbol(currencyPair.toString()),
            periodType.code(),
            defaultCandleStickParam.getStartDate().getTime(),
            defaultCandleStickParam.getEndDate().getTime());
    return CoinbaseAdapters.adaptBinanceCandleStickData(klines, currencyPair, periodType);
  }
}
