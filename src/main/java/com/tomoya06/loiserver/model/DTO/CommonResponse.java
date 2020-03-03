package com.tomoya06.loiserver.model.DTO;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CommonResponse {

  private Integer code;
  private String status;
  private Object msg;
  private Object data;

  public CommonResponse(HttpStatus status, Object data) {
    this.code = status.value();
    this.msg = status.getReasonPhrase();
    if (!status.is2xxSuccessful() || !status.is3xxRedirection()) {
      this.msg = data;
    } else {
      this.data = data;
    }
  }
}
