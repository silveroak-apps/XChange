package org.knowm.xchange.coinmarketcap.pro.v1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class CMCCandleStickParam implements CandleStickDataParams {
    private final Date timeStart;
    private final Date timeEnd;
    private final long periodInSecs;
    private final String limit;
    private final String cmcId;
    private final int convertId = 2781;

    public String getPeriodShortString() {
        if (periodInSecs == 60) {
            return "1m";
        } else if (periodInSecs == 300) {
            return "5m";
        } else if (periodInSecs == 900) {
            return "15m";
        } else if (periodInSecs == 1800) {
            return "30m";
        } else if (periodInSecs == 3600) {
            return "1h";
        } else if (periodInSecs == 7200) {
            return "2h";
        } else if (periodInSecs == 14400) {
            return "4h";
        } else if (periodInSecs == 21600) {
            return "6h";
        } else if (periodInSecs == 43200) {
            return "12h";
        } else if (periodInSecs == 86400) {
            return "1d";
        } else if (periodInSecs == 259200) {
            return "3d";
        } else if (periodInSecs == 604800) {
            return "1w";
        } else if (periodInSecs == 2592000) {
            return "1M";
        } else {
            return "1d";
        }
    }

}
