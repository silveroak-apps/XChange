package org.knowm.xchange.bybit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.utils.jackson.UnixTimestampNanoSecondsDeserializer;

import java.util.Date;

@Builder
@Jacksonized
@Value
public class BybitV5Response<V> {

  @JsonProperty("retCode")
  int retCode;

  @JsonProperty("retMsg")
  String retMsg;

  @JsonProperty("retExtInfo")
  Object retExtInfo;

  @JsonProperty("result")
  V result;

  @JsonProperty("time")
  @JsonDeserialize(using = UnixTimestampNanoSecondsDeserializer.class)
  Date timeNow;

  public boolean isSuccess() {
    return retCode == 0;
  }
}
