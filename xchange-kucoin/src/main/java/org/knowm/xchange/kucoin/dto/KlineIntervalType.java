package org.knowm.xchange.kucoin.dto;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * The Kucoin KlineInterval is similar to the Binance KlineInterval enum. This is used to convert
 * back and forth between the string candlestick type patterns and durations in seconds for each
 * type. The seconds property can be useful when doing time calculations for fetch periods.
 *
 * <p>The supported pattern types are: 1min, 3min, 5min, 15min, 30min, 1hour, 2hour, 4hour, 6hour,
 * 8hour, 12hour, 1day, 1week
 *
 * <p>See https://docs.kucoin.com/#get-klines for more info kline fetch.
 */
public enum KlineIntervalType {
  m1("1min", MINUTES.toSeconds(1)),
  m3("3min", MINUTES.toSeconds(3)),
  m5("5min", MINUTES.toSeconds(5)),
  m15("15min", MINUTES.toSeconds(15)),
  m30("30min", MINUTES.toSeconds(30)),

  h1("1hour", HOURS.toSeconds(1)),
  h2("2hour", HOURS.toSeconds(2)),
  hour4("4hour", HOURS.toSeconds(4)),
  h6("6hour", HOURS.toSeconds(6)),
  h8("8hour", HOURS.toSeconds(8)),
  h12("12hour", HOURS.toSeconds(12)),

  d1("1day", DAYS.toSeconds(1)),

  w1("1week", DAYS.toSeconds(7));

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
