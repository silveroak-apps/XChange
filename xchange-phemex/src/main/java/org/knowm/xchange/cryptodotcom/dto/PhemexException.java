package org.knowm.xchange.cryptodotcom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

@SuppressWarnings("serial")
public class PhemexException extends HttpStatusExceptionSupport {

  public PhemexException(
          @JsonProperty("error") String message, @JsonProperty("success") Boolean success) {
    super(message);
  }
}
