package org.knowm.xchange.gemini.v1.dto.marketdata;

import java.time.Duration;

import static java.util.concurrent.TimeUnit.*;

public enum KlineInterval {
  m1("1m", MINUTES.toMillis(1)),
  m5("5m", MINUTES.toMillis(5)),
  m15("15m", MINUTES.toMillis(15)),
  m30("30m", MINUTES.toMillis(30)),
  h1("1h", HOURS.toMillis(1)),
  h6("6h", HOURS.toMillis(6)),
  d1("1D", DAYS.toMillis(1));

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
