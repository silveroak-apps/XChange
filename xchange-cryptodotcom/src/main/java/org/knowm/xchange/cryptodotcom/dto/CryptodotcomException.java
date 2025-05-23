package org.knowm.xchange.cryptodotcom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

@SuppressWarnings("serial")
public class CryptodotcomException extends HttpStatusExceptionSupport {

  public CryptodotcomException(
          @JsonProperty("error") String message, @JsonProperty("success") Boolean success) {
    super(message);
  }
}
