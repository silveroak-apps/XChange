package org.knowm.xchange.phemex.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class PhemexKlineCandleData {
    public int code;
    public String method;
    public Result result;

    @Getter
    @Setter
    public class Datum{
        public Long t;
        public double o;
        public double h;
        public double l;
        public double c;
        public double v;
    }

    @Getter
    @Setter
    public class Result{
        public String instrument_name;
        public String interval;
        public ArrayList<Datum> data;
    }
}
