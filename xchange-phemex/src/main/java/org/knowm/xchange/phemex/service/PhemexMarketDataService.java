package org.knowm.xchange.phemex.service;

import org.knowm.xchange.Exchange;

import org.knowm.xchange.phemex.PhemexAdapters;
import org.knowm.xchange.phemex.dto.PhemexKlineInterval;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;

/** @author jamespedwards42 */
public class PhemexMarketDataService extends PhemexMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public PhemexMarketDataService(Exchange exchange) {

    super(exchange);
  }



  @Override
  public CandleStickData getCandleStickData(CurrencyPair currencyPair, CandleStickDataParams params) throws IOException {

    if (!(params instanceof DefaultCandleStickParam)) {
      throw new NotYetImplementedForExchangeException("Only DefaultCandleStickParam is supported");
    }
    DefaultCandleStickParam defaultCandleStickParam = (DefaultCandleStickParam) params;
    PhemexKlineInterval periodType =
            PhemexKlineInterval.getPeriodTypeFromSecs(defaultCandleStickParam.getPeriodInSecs());
    if (periodType == null) {
      throw new NotYetImplementedForExchangeException("Only discrete period values are supported;" +
              Arrays.toString(PhemexKlineInterval.getSupportedPeriodsInSecs()));
    }

    LinkedHashMap klines = getCoinbaseHistoricalCandles(PhemexAdapters.convertToCryptodotomSymbol(currencyPair.toString()),
            defaultCandleStickParam.getStartDate().getTime(),
            defaultCandleStickParam.getEndDate().getTime(),
            PhemexKlineInterval.getPeriodTypeFromSecs(defaultCandleStickParam.getPeriodInSecs()).code());
    return PhemexAdapters.adaptCryptodotcomCandleStickData(klines, currencyPair, periodType);
  }
}
