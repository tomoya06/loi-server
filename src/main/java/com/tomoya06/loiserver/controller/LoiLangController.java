package com.tomoya06.loiserver.controller;

import com.tomoya06.loiserver.model.DTO.SuccessResponse;
import com.tomoya06.loiserver.model.query.CreateLoiLangQuery;
import com.tomoya06.loiserver.service.LoiLangService;
import java.security.InvalidParameterException;
import javax.management.InstanceAlreadyExistsException;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/loilang")
public class LoiLangController {

  @Autowired
  private LoiLangService loiLangService;

  @GetMapping("/search")
  ResponseEntity<?> search(@RequestParam String query) {
    var result = loiLangService.search(query);
    return new ResponseEntity<>(new SuccessResponse(result), HttpStatus.OK);
  }

  @GetMapping("/get/{word}")
  ResponseEntity<?> getWord(@PathVariable String word) throws NullPointerException {
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
    loiLangService.create(query.getWord(), query.getPrefix(), query.getSuffix(), query.getType());
    return new ResponseEntity<>(new SuccessResponse(true), HttpStatus.OK);
  }
}
