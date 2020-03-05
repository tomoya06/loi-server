package com.tomoya06.loiserver.loilang.model.DTO;

import org.springframework.http.HttpStatus;

/**
 * @author pengjiahui
 */
public class SuccessResponse extends CommonResponse {

  public SuccessResponse(Object data) {
    super(HttpStatus.OK, data);
  }
}