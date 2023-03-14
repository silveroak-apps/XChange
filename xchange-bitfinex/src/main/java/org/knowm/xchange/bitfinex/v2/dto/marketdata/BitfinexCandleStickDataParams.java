package org.knowm.xchange.bitfinex.v2.dto.marketdata;

import org.knowm.xchange.service.trade.params.CandleStickDataParams;

public class BitfinexCandleStickDataParams implements CandleStickDataParams {

    private final String candlePeriod;
    private final Integer limit;
    private final Long start;
    private final Long end;

    public BitfinexCandleStickDataParams(
            String candlePeriod, Integer limit, Long start, Long end) {
        this.candlePeriod = candlePeriod;
        this.limit = limit;
        this.start = start;
        this.end = end;
    }

    public String getCandlePeriod() {
        return candlePeriod;
    }

    public Integer getLimit() {
        return limit;
    }

    public Long getStart() {
        return start;
    }

    public Long getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "BitfinexCandleStickDataParams{"
                + "granularity="
                + candlePeriod
                + ", limit="
                + limit
                + ", start="
                + start
                + ", end="
                + end
                + '}';
    }
}
