package org.knowm.xchange.gateio.v4.dto;

import static java.util.concurrent.TimeUnit.*;

public enum KlineInterval {
  m1("10s", SECONDS.toMillis(10)),
  m3("1m", MINUTES.toMillis(1)),
  m5("5m", MINUTES.toMillis(5)),
  m15("15m", MINUTES.toMillis(15)),
  m30("30m", MINUTES.toMillis(30)),

  h1("1h", HOURS.toMillis(1)),
  h4("4h", HOURS.toMillis(4)),
  h8("8h", HOURS.toMillis(8)),

  d1("1D", DAYS.toMillis(1)),

  w1("7D", DAYS.toMillis(7)),

  M1("30D", DAYS.toMillis(30));

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
