package org.knowm.xchange.poloniex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.poloniex.Poloniex;
import org.knowm.xchange.poloniex.PoloniexAdapters;
import org.knowm.xchange.poloniex.PoloniexErrorAdapter;
import org.knowm.xchange.poloniex.PoloniexUtils;
import org.knowm.xchange.poloniex.dto.PoloniexException;
import org.knowm.xchange.poloniex.dto.marketdata.*;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParamWithLimit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/** @author Zach Holmes */
public class PoloniexMarketDataService extends PoloniexMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange Exchange instance for the service
   */
  public PoloniexMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public CandleStickData getCandleStickData (CurrencyPair currencyPair, CandleStickDataParams params)
      throws IOException {
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

    int limit = 5;
    if (params instanceof DefaultCandleStickParamWithLimit) {
      limit = ((DefaultCandleStickParamWithLimit) params).getLimit();
    }

    ArrayList<ArrayList<Object>> historyCandle = getCandlestickChartData(
            currencyPair,
            defaultCandleStickParam.getStartDate().getTime(),
            defaultCandleStickParam.getEndDate().getTime(),
            periodType.code(), limit);
    return PoloniexAdapters.adaptPoloniexCandleStickData(historyCandle, currencyPair, periodType);
  }
}
