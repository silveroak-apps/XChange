package org.knowm.xchange.huobi.dto.marketdata;

import org.knowm.xchange.service.trade.params.CandleStickDataParams;

public class HuobiCandleStickParam implements CandleStickDataParams {
    private final int size;
    private final long periodInSecs;

    public HuobiCandleStickParam( int size, long periodInSecs) {

        this.size = size;
        this.periodInSecs = periodInSecs;
    }

    public long getPeriodInSecs() {
        return periodInSecs;
    }

    public int getSize() {
        return size;
    }
}
