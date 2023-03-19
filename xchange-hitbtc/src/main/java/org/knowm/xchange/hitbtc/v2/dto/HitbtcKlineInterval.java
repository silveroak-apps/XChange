package org.knowm.xchange.hitbtc.v2.dto;

import static java.util.concurrent.TimeUnit.*;

//Accepted values: M1 (one minute), M3, M5, M15, M30, H1 (one hour), H4, D1 (one day), D7, 1M (one month)
public enum HitbtcKlineInterval {
    m1("M1", MINUTES.toMillis(1)),
    m3("M3", MINUTES.toMillis(3)),
    m5("M5", MINUTES.toMillis(5)),
    m15("M15", MINUTES.toMillis(15)),
    m30("M30", MINUTES.toMillis(30)),
    h1("H1", HOURS.toMillis(1)),
    h4("H4", HOURS.toMillis(4)),
    d1("D1", DAYS.toMillis(1)),
    d7("D7", DAYS.toMillis(7)),
    M1("1M", DAYS.toMillis(30))

  ;


  private final String code;
  private final Long millis;

  private HitbtcKlineInterval(String code, Long millis) {
    this.millis = millis;
    this.code = code;
  }

  public Long getMillis() {
    return millis;
  }

  public String code() {
    return code;
  }

  public static HitbtcKlineInterval getPeriodTypeFromSecs(long periodInSecs) {
      HitbtcKlineInterval result = null;
    for (HitbtcKlineInterval period : HitbtcKlineInterval.values()) {
      if (period.millis == periodInSecs * 1000) {
        result = period;
        break;
      }
    }
    return result;
  }

  public static long[] getSupportedPeriodsInSecs() {
    long[] result = new long[HitbtcKlineInterval.values().length];
    int index = 0;
    for (HitbtcKlineInterval period : HitbtcKlineInterval.values()) {
      result[index++] = period.millis;
    }
    return result;
  }
}
