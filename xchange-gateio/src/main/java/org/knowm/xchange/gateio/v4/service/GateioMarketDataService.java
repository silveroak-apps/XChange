package org.knowm.xchange.gateio.v4.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.dto.marketdata.GateioDepth;
import org.knowm.xchange.gateio.dto.marketdata.GateioTicker;
import org.knowm.xchange.gateio.dto.marketdata.GateioTradeHistory;
import org.knowm.xchange.gateio.v4.dto.KlineInterval;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam;
import org.knowm.xchange.service.marketdata.params.Params;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    return GateioAdapters.adaptBinanceCandleStickData(klikes, currencyPair, KlineInterval.getPeriodTypeFromSecs(defaultCandleStickParam.getPeriodInSecs()));
  }
}
