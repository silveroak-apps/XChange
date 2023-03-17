package org.knowm.xchange.cryptodotcom.dto;

import static java.util.concurrent.TimeUnit.*;

public enum PhemexKlineInterval {
  m1(60, MINUTES.toMillis(1)),
  m5(300, MINUTES.toMillis(5)),
  m15(900, MINUTES.toMillis(15)),
  m30(1800, MINUTES.toMillis(30)),
  h1(3600, HOURS.toMillis(1)),
  h4(14400, HOURS.toMillis(4)),
  d1(86400, DAYS.toMillis(1)),
  M1(2592000, DAYS.toMillis(30));


  private final Integer code;
  private final Long millis;

  private PhemexKlineInterval(Integer code, Long millis) {
    this.millis = millis;
    this.code = code;
  }

  public Long getMillis() {
    return millis;
  }

  public Integer code() {
    return code;
  }

  public static PhemexKlineInterval getPeriodTypeFromSecs(long periodInSecs) {
    PhemexKlineInterval result = null;
    for (PhemexKlineInterval period : PhemexKlineInterval.values()) {
      if (period.millis == periodInSecs * 1000) {
        result = period;
        break;
      }
    }
    return result;
  }

  public static long[] getSupportedPeriodsInSecs() {
    long[] result = new long[PhemexKlineInterval.values().length];
    int index = 0;
    for (PhemexKlineInterval period : PhemexKlineInterval.values()) {
      result[index++] = period.millis;
    }
    return result;
  }
}
