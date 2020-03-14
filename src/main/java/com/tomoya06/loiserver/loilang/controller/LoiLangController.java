package com.tomoya06.loiserver.loilang.controller;

import com.tomoya06.loiserver.loilang.model.DTO.SuccessResponse;
import com.tomoya06.loiserver.loilang.service.LoiLangService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/loilang")
public class LoiLangController {

  @Autowired
  private LoiLangService loiLangService;

  @GetMapping("/general")
  ResponseEntity<?> general() {
    var result = loiLangService.getGeneral();
    return new ResponseEntity<>(new SuccessResponse(result), HttpStatus.OK);
  }

  @GetMapping("/search")
  ResponseEntity<?> search(
      @RequestParam String query,
      @RequestParam(defaultValue = "false") Boolean dizzy) {
    var result = loiLangService.search(query, dizzy);
    return new ResponseEntity<>(new SuccessResponse(result), HttpStatus.OK);
  }

  @GetMapping("/get")
  ResponseEntity<?> getWord(@RequestParam String word) throws NullPointerException {
    var result = loiLangService.getWord(word);
    if (result == null) {
      throw new NullPointerException();
    }
    return new ResponseEntity<>(new SuccessResponse(result), HttpStatus.OK);
  }
}
