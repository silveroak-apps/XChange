package org.knowm.xchange.mexc.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.mexc.dto.marketdata.MEXCCandleStick;

import java.io.IOException;
import java.util.List;

public class MEXCMarketDataServiceRaw extends MEXCBaseService {
    public MEXCMarketDataServiceRaw(Exchange exchange) {
        super(exchange);
    }

    public List<MEXCCandleStick> getCandleSticks(CurrencyPair currencyPair, String timeWindow, long start, long end, int limit) throws IOException {
        return mexcAuthenticated.getCandles(currencyPair.base.getCurrencyCode() + "" + currencyPair.counter.getCurrencyCode(),
                timeWindow, start, end, limit);
    }
}
