package org.knowm.xchange.btcmarkets.dto.v3.marketdata;

import static java.util.concurrent.TimeUnit.*;

//1m, 3m, 5m, 15m, 30m, 1h, 2h, 3h, 4h, 6h, 1d, 1w and 1mo
public enum BTCMarketsKlineInterval {
    m1("1m", MINUTES.toMillis(1)),
    m3("3m", MINUTES.toMillis(3)),
    m5("5m", MINUTES.toMillis(5)),
    m15("15m", MINUTES.toMillis(15)),
    m30("30m", MINUTES.toMillis(30)),
    h1("1h", HOURS.toMillis(1)),
    h2("2h", HOURS.toMillis(2)),
    h3("3h", HOURS.toMillis(3)),
    h4("4h", HOURS.toMillis(4)),
    h6("6h", HOURS.toMillis(6)),
    d1("1d", DAYS.toMillis(1)),
    w1("1w", DAYS.toMillis(7)),
    M1("1mo", DAYS.toMillis(30))

  ;


  private final String code;
  private final Long millis;

  private BTCMarketsKlineInterval(String code, Long millis) {
    this.millis = millis;
    this.code = code;
  }

  public Long getMillis() {
    return millis;
  }

  public String code() {
    return code;
  }

  public static BTCMarketsKlineInterval getPeriodTypeFromSecs(long periodInSecs) {
    BTCMarketsKlineInterval result = null;
    for (BTCMarketsKlineInterval period : BTCMarketsKlineInterval.values()) {
      if (period.millis == periodInSecs * 1000) {
        result = period;
        break;
      }
    }
    return result;
  }

  public static long[] getSupportedPeriodsInSecs() {
    long[] result = new long[BTCMarketsKlineInterval.values().length];
    int index = 0;
    for (BTCMarketsKlineInterval period : BTCMarketsKlineInterval.values()) {
      result[index++] = period.millis;
    }
    return result;
  }
}
