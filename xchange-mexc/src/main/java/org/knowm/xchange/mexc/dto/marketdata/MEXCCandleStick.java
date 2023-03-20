package org.knowm.xchange.mexc.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

/* Sample message:
[
"1640804880000",
"15100",
"15200",
"15100",
"15199",
"4.11970335"
]
*/
public class MEXCCandleStick {



  private final Long timestamp;
  private final String openPrice;
  private final String highPrice;
  private final String lowPrice;
  private final String closePrice;
  private final String volume;
  private final Long closetime;

  @JsonCreator
  public MEXCCandleStick(JsonNode node) {
    this.timestamp = node.get(0).asLong();
    this.openPrice = node.get(1).asText();
    this.highPrice = node.get(2).asText();
    this.lowPrice = node.get(3).asText();
    this.closePrice = node.get(4).asText();
    this.volume = node.get(5).asText();
    this.closetime = node.get(6).asLong();
  }

  public Long  getTimestamp() {
    return timestamp;
  }

  public String getOpenPrice() {
    return openPrice;
  }

  public String getClosePrice() {
    return closePrice;
  }

  public String getHighPrice() {
    return highPrice;
  }

  public String getLowPrice() {
    return lowPrice;
  }

  public String getVolume() {
    return volume;
  }

  public Long  getClosetime() {
    return closetime;
  }

}
