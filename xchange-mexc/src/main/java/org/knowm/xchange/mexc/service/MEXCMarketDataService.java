package org.knowm.xchange.mexc.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.mexc.MEXCAdapters;
import org.knowm.xchange.mexc.MEXCExchange;
import org.knowm.xchange.mexc.dto.marketdata.KlineIntervalType;
import org.knowm.xchange.mexc.dto.marketdata.MEXCCandleStick;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParamWithLimit;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MEXCMarketDataService extends MEXCMarketDataServiceRaw implements MarketDataService {

  public MEXCMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public CandleStickData getCandleStickData(CurrencyPair currencyPair, CandleStickDataParams params) throws IOException {
    if (!(params instanceof DefaultCandleStickParam)) {
      throw new NotYetImplementedForExchangeException("Only DefaultCandleStickParam is supported");
    }
    DefaultCandleStickParam defaultCandleStickParam = (DefaultCandleStickParam) params;
    KlineIntervalType periodType =
            KlineIntervalType.getPeriodTypeFromSecs(defaultCandleStickParam.getPeriodInSecs());
    if (periodType == null) {
      throw new NotYetImplementedForExchangeException("Only discrete period values are supported;" +
              Arrays.toString(KlineIntervalType.getSupportedPeriodsInSecs()));
    }

    int limit = 5;
    if (params instanceof DefaultCandleStickParamWithLimit) {
      limit = ((DefaultCandleStickParamWithLimit) params).getLimit();
    }

    List<MEXCCandleStick> historyCandle = getCandleSticks(currencyPair, periodType.code(),
            defaultCandleStickParam.getStartDate().getTime(), defaultCandleStickParam.getEndDate().getTime(),
            limit);
    return MEXCAdapters.adaptCandleStickData(historyCandle, currencyPair, periodType);
  }
}
