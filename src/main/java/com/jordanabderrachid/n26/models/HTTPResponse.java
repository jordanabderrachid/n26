package com.jordanabderrachid.n26.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HTTPResponse {

  private int code;

  private String status;

  private String message;

  public HTTPResponse(int code, String status) {
    this.code = code;
    this.status = status;
    this.message = null;
  }

  public HTTPResponse(int code, String status, String message) {
    this.code = code;
    this.status = status;
    this.message = message;
  }

  @JsonProperty("code")
  public int getCode() { return this.code; }

  @JsonProperty("status")
  public String getStatus() { return this.status; }

  @JsonProperty("message")
  public String getMessage() { return this.message; }
}
