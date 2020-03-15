package com.tomoya06.loiserver.common.model;

import org.springframework.http.HttpStatus;

public class FailureResponse extends CommonResponse {

  public FailureResponse(HttpStatus status, Object data) {
    super(status, data);
  }
}
