package com.tomoya06.loiserver.controller;

import com.tomoya06.loiserver.model.DTO.SuccessResponse;
import com.tomoya06.loiserver.service.LoiLangService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/loilang")
public class LoiLangController {

  @Autowired
  private LoiLangService loiLangService;

  @GetMapping("/test")
  ResponseEntity<?> test() {
    var result = loiLangService.test();
    return new ResponseEntity<>(new SuccessResponse(result), HttpStatus.OK);
  }
}
