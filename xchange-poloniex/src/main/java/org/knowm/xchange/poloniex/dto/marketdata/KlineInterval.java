package org.knowm.xchange.poloniex.dto.marketdata;

import static java.util.concurrent.TimeUnit.*;

public enum KlineInterval {
  //, MINUTE_5, MINUTE_10, MINUTE_15, MINUTE_30, HOUR_1, HOUR_2, HOUR_4, HOUR_6, HOUR_12, DAY_1, DAY_3, WEEK_1 and MONTH_1
  m1("MINUTE_1", MINUTES.toMillis(1)),
  m5("MINUTE_5", MINUTES.toMillis(5)),
  m10("MINUTE_10", MINUTES.toMillis(10)),
  m15("MINUTE_15", MINUTES.toMillis(15)),
  m30("MINUTE_30", MINUTES.toMillis(30)),
  h1("HOUR_1", HOURS.toMillis(1)),
  h2("HOUR_1", HOURS.toMillis(2)),
  h4("HOUR_1", HOURS.toMillis(4)),
  h6("HOUR_1", HOURS.toMillis(6)),
  h12("HOUR_1", HOURS.toMillis(12)),

  d1("DAY_1", DAYS.toMillis(1)),
  d3("DAY_3", DAYS.toMillis(1)),
  W1("WEEK_1", DAYS.toMillis(7)),
  M1("MONTH_1", DAYS.toMillis(30));

  private final String code;
  private final Long millis;

  private KlineInterval(String code, Long millis) {
    this.millis = millis;
    this.code = code;
  }

  public Long getMillis() {
    return millis;
  }

  public String code() {
    return code;
  }

  public static KlineInterval getPeriodTypeFromSecs(long periodInSecs) {
    KlineInterval result = null;
    for (KlineInterval period : KlineInterval.values()) {
      if (period.millis == periodInSecs * 1000) {
        result = period;
        break;
      }
    }
    return result;
  }

  public static long[] getSupportedPeriodsInSecs() {
    long[] result = new long[KlineInterval.values().length];
    int index = 0;
    for (KlineInterval period : KlineInterval.values()) {
      result[index++] = period.millis;
    }
    return result;
  }
}
