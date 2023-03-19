package org.knowm.xchange.hitbtc.v2.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class HitbtcCandle {

  private final String timestamp;

  private final BigDecimal open;

  private final BigDecimal close;

  private final BigDecimal min;

  private final BigDecimal max;

  private final BigDecimal volume;

  private final BigDecimal volumeQuote;

  @JsonCreator
  public HitbtcCandle(
      @JsonProperty("timestamp") String timestamp,
      @JsonProperty("open") BigDecimal open,
      @JsonProperty("close") BigDecimal close,
      @JsonProperty("min") BigDecimal min,
      @JsonProperty("max") BigDecimal max,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("volumeQuote") BigDecimal volumeQuote) {
    this.timestamp = timestamp;
    this.open = open;
    this.close = close;
    this.min = min;
    this.max = max;
    this.volume = volume;
    this.volumeQuote = volumeQuote;
  }

  @Override
  public String toString() {
    return "HitbtcCandle [timestamp="
        + timestamp
        + ", open="
        + open
        + ", max="
        + max
        + ", min="
        + min
        + ", close="
        + close
        + ", volumeQuote="
        + volumeQuote
        + ", volume="
        + volume
        + "]";
  }

  public String getTimestamp() {
    return timestamp;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal getClose() {
    return close;
  }

  public BigDecimal getMin() {
    return min;
  }

  public BigDecimal getMax() {
    return max;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getVolumeQuote() {
    return volumeQuote;
  }
}
