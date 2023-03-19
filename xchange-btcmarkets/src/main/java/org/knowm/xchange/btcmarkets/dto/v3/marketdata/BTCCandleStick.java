package org.knowm.xchange.btcmarkets.dto.v3.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

/* Sample message:
[
"2019-09-02T18:00:00.000000Z",
"15100",
"15200",
"15100",
"15199",
"4.11970335"
]
*/
public class BTCCandleStick {



  private final String timestamp;
  private final String openPrice;
  private final String highPrice;
  private final String lowPrice;
  private final String closePrice;
  private final String volume;

  @JsonCreator
  public BTCCandleStick(JsonNode node) {
    this.timestamp = node.get(0).asText();
    this.openPrice = node.get(1).asText();
    this.highPrice = node.get(2).asText();
    this.lowPrice = node.get(3).asText();
    this.closePrice = node.get(4).asText();
    this.volume = node.get(5).asText();
  }

  public String  getTimestamp() {
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

}
