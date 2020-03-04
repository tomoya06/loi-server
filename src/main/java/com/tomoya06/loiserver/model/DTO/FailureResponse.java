package com.tomoya06.loiserver.model.DTO;

import org.springframework.http.HttpStatus;

public class FailureResponse extends CommonResponse {

  public FailureResponse(HttpStatus status, Object data) {
    super(status, data);
  }
}
