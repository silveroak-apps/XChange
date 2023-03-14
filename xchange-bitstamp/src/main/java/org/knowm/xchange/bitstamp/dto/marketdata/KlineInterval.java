package org.knowm.xchange.bitstamp.dto.marketdata;

import static java.util.concurrent.TimeUnit.*;

public enum KlineInterval {
  m1(60, MINUTES.toMillis(1)),
  m3(180, MINUTES.toMillis(3)),
  m5(300, MINUTES.toMillis(5)),
  m15(900, MINUTES.toMillis(15)),
  m30(1800, MINUTES.toMillis(30)),
  h1(3600, HOURS.toMillis(1)),
  h2(7200, HOURS.toMillis(2)),
  h4(14400, HOURS.toMillis(4)),
  h6(21600, HOURS.toMillis(6)),
  h12(43200, HOURS.toMillis(12)),
  d1(86400, DAYS.toMillis(1)),
  d3(259200, DAYS.toMillis(3));


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
