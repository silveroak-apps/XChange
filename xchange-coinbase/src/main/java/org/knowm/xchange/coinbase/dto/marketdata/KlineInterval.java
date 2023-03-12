package org.knowm.xchange.coinbase.dto.marketdata;

import static java.util.concurrent.TimeUnit.*;

public enum KlineInterval {
  m1(60, MINUTES.toMillis(1)),
  m5(300, MINUTES.toMillis(5)),
  m15(900, MINUTES.toMillis(15)),
  h1(3600, HOURS.toMillis(1)),
  h6(21600, HOURS.toMillis(6)),
  d1(86400, DAYS.toMillis(1));

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
