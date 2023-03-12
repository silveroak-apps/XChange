package org.knowm.xchange.gateio.v4.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.v4.dto.KlineInterval;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GateioMarketDataService extends GateioMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public CandleStickData getCandleStickData(CurrencyPair currencyPair, CandleStickDataParams params)
      throws IOException {
    DefaultCandleStickParam defaultCandleStickParam = (DefaultCandleStickParam) params;
    List<ArrayList<Object>> klikes = getKlines(currencyPair,
            KlineInterval.getPeriodTypeFromSecs(defaultCandleStickParam.getPeriodInSecs()).code(),
            defaultCandleStickParam.getStartDate().getTime(),
            defaultCandleStickParam.getEndDate().getTime()
            );

    return GateioAdapters.adaptGateioCandleStickData(klikes, currencyPair, KlineInterval.getPeriodTypeFromSecs(defaultCandleStickParam.getPeriodInSecs()));
  }
}
