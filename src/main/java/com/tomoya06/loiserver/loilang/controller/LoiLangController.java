package com.tomoya06.loiserver.loilang.controller;

import com.tomoya06.loiserver.loilang.model.DTO.SuccessResponse;
import com.tomoya06.loiserver.loilang.model.query.AddPronQuery;
import com.tomoya06.loiserver.loilang.model.query.CreateLoiLangQuery;
import com.tomoya06.loiserver.loilang.model.query.RemoveWordQuery;
import com.tomoya06.loiserver.loilang.service.LoiLangService;
import java.security.InvalidParameterException;
import javax.management.InstanceAlreadyExistsException;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  ResponseEntity<?> search(@RequestParam String query) {
    var result = loiLangService.search(query);
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

  @PostMapping("/create")
  ResponseEntity<?> create(@RequestBody CreateLoiLangQuery query)
      throws InstanceAlreadyExistsException, InvalidParameterException {
    if (!query.isValid()) {
      throw new InvalidParameterException();
    }
    var result = loiLangService.create(query.getWord(), query.getPinyin(), query.getType());
    return new ResponseEntity<>(new SuccessResponse(result), HttpStatus.OK);
  }

  @PostMapping("/add/pron")
  ResponseEntity<?> addPron(@RequestBody AddPronQuery query)
      throws InstanceAlreadyExistsException, InvalidParameterException {
    if (!query.isValid()) {
      throw new InvalidParameterException();
    }
    var result = loiLangService.addMultiPron(query.getWord(), query.getPinyin());
    return new ResponseEntity<>(new SuccessResponse(result), HttpStatus.OK);
  }

  @PostMapping("/remove/pinyin")
  ResponseEntity<?> removePinyin(@RequestBody AddPronQuery query) throws InvalidParameterException {
    if (!query.isValid()) {
      throw new InvalidParameterException();
    }
    var result = loiLangService.deletePinyin(query.getWord(), query.getPinyin());
    return new ResponseEntity<>(new SuccessResponse(result), HttpStatus.OK);
  }

  @PostMapping("/remove/word")
  ResponseEntity<?> removeWord(@RequestBody RemoveWordQuery query) {
    if (!query.isValid()) {
      throw new InvalidParameterException();
    }
    var result = loiLangService.deleteWord(query.getWord());
    return new ResponseEntity<>(new SuccessResponse(result), HttpStatus.OK);
  }
}
