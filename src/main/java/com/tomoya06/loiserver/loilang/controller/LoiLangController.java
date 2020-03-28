package com.tomoya06.loiserver.loilang.controller;

import com.tomoya06.loiserver.common.model.SuccessResponse;
import com.tomoya06.loiserver.loilang.service.LoiLangService;
import com.tomoya06.loiserver.loilang.service.LoiLangSubService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/loilang")
public class LoiLangController {

  @Autowired
  private LoiLangService loiLangService;

  @Autowired
  private LoiLangSubService loiLangSubService;

  @GetMapping("/search")
  public ResponseEntity<?> search(
      @RequestParam String query,
      @RequestParam(defaultValue = "false") Boolean dizzy) {
    var result = loiLangService.searchWord(query, dizzy);
    return new ResponseEntity<>(new SuccessResponse(result), HttpStatus.OK);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<?> getWordById(@PathVariable String id) {
    var result = loiLangService.getWordByTargetId(id);
    if (result == null) {
      throw new NullPointerException();
    }
    return new ResponseEntity<>(new SuccessResponse(result), HttpStatus.OK);
  }

  @GetMapping("/rec")
  public ResponseEntity<?> getRecommend() {
    var result = loiLangSubService.getRecommend();
    return new ResponseEntity<>(new SuccessResponse(result), HttpStatus.OK);
  }

  @GetMapping("/general")
  public ResponseEntity<?> getGeneral() {
    var result = loiLangSubService.getGeneral();
    return new ResponseEntity<>(new SuccessResponse(result), HttpStatus.OK);
  }
}
