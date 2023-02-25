package org.knowm.xchange.bybit.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;

@Jacksonized
@Builder
@ToString
@Getter
@Setter
public class BybitV5Result {

    public List<BybitKline> bybitOHLCVS;
    @JsonProperty("list")
    public ArrayList<List<String>> list;
    public String symbol;
    public String category;

//    @JsonCreator
//    public BybitV5Result(List<BybitKline> bybitOHLCVS) {
//            this.bybitOHLCVS = bybitOHLCVS;
//    }

    public List<BybitKline> getBybitOHLCVS() {
        if (bybitOHLCVS == null) {
            bybitOHLCVS = new ArrayList<>();
            for (List<String> l : list) {
                BybitKline kline = new BybitKline(l.get(0), l.get(1), l.get(2), l.get(3), l.get(4), l.get(5), l.get(6));
                bybitOHLCVS.add(kline);
            }
            setBybitOHLCVS(bybitOHLCVS);
        }
        return bybitOHLCVS;
    }
}
