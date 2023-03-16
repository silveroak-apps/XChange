package org.knowm.xchange.ascendex.dto.marketdata;

import static java.util.concurrent.TimeUnit.*;

public enum KlineInterval {
  m1(1, MINUTES.toMillis(1)),
  m5(5, MINUTES.toMillis(5)),
  m15(15, MINUTES.toMillis(15)),
  m30(30, MINUTES.toMillis(30)),

  h1(60, HOURS.toMillis(1)),
  h2(120, HOURS.toMillis(2)),
  h4(240, HOURS.toMillis(4)),

  h12(720, HOURS.toMillis(12)),

  d1(1440, DAYS.toMillis(1));


  private final Integer code;
  private final Long millis;

  private KlineInterval(Integer code, Long millis) {
    this.millis = millis;
    this.code = code;
  }

  public Long getMillis() {
    return millis;
  }

  public Integer code() {
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
