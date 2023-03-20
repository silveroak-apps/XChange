package org.knowm.xchange.mexc.dto.marketdata;

import static java.util.concurrent.TimeUnit.*;

/**
 * 1m 1 minute
 * 5m 5 minute
 * 15m 15 minute
 * 30m 30 minute
 * 60m 60 minute
 * 4h 4 hour
 * 1d 1 day
 * 1M 1 month
 */
public enum KlineIntervalType {
    m1("1m", MINUTES.toSeconds(1)),
    m5("5m", MINUTES.toSeconds(5)),
    m15("15m", MINUTES.toSeconds(15)),
    m30("30m", MINUTES.toSeconds(30)),
    h1("60m", HOURS.toSeconds(1)),
    h4("4h", HOURS.toSeconds(4)),
    d1("1d", DAYS.toSeconds(1)),
    M1("1M", DAYS.toSeconds(30))

 ;

  private final String code;
  private final Long seconds;

  private KlineIntervalType(String code, Long seconds) {
    this.seconds = seconds;
    this.code = code;
  }

  public Long getSeconds() {
    return seconds;
  }

  public String code() {
    return code;
  }

  public static KlineIntervalType getPeriodTypeFromSecs(long periodInSecs) {
    KlineIntervalType result = null;
    for (KlineIntervalType period : KlineIntervalType.values()) {
      if (period.seconds == periodInSecs) {
        result = period;
        break;
      }
    }
    return result;
  }

  public Long getMillis() { return seconds * 1000;}

  public static long[] getSupportedPeriodsInSecs() {
    long[] result = new long[KlineIntervalType.values().length];
    int index = 0;
    for (KlineIntervalType period : KlineIntervalType.values()) {
      result[index++] = period.seconds;
    }
    return result;
  }
}
