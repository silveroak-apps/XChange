package org.knowm.xchange.bybit.dto.marketdata;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;


@Getter
@Setter
@Jacksonized
@Builder
public class BybitKline {


    public String openTime;
    public String open;
    public String high;
    public String low;
    public String close;
    public String volume;
    public String turnover;

    }


