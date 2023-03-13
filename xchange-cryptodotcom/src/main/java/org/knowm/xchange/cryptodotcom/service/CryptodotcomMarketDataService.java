package org.knowm.xchange.cryptodotcom.service;

import org.knowm.xchange.Exchange;

import org.knowm.xchange.cryptodotcom.CryptodotcomAdapters;
import org.knowm.xchange.cryptodotcom.dto.KlineCandleData;
import org.knowm.xchange.cryptodotcom.dto.KlineInterval;
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
public class CryptodotcomMarketDataService extends CryptodotcomMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptodotcomMarketDataService(Exchange exchange) {

    super(exchange);
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

    LinkedHashMap klines = getCoinbaseHistoricalCandles(CryptodotcomAdapters.convertToCryptodotomSymbol(currencyPair.toString()),
            KlineInterval.getPeriodTypeFromSecs(defaultCandleStickParam.getPeriodInSecs()).code());
    return CryptodotcomAdapters.adaptCryptodotcomCandleStickData(klines, currencyPair, periodType);
  }
}
