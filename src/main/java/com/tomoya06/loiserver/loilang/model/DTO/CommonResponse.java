package com.tomoya06.loiserver.loilang.model.DTO;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CommonResponse {

  private Integer code;
  private String status;
  private Object data;

  public CommonResponse(HttpStatus status, Object data) {
    this.code = status.value();
    this.status = status.getReasonPhrase();
    this.data = data;
  }
}
