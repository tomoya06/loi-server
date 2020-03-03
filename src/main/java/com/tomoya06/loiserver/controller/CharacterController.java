package com.tomoya06.loiserver.controller;

import com.tomoya06.loiserver.model.DTO.Character;
import com.tomoya06.loiserver.model.DTO.SuccessResponse;
import com.tomoya06.loiserver.model.query.AddPinyinQuery;
import com.tomoya06.loiserver.service.CharacterService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author pengjiahui
 */
@Controller
@RequestMapping("/api/pinyin")
public class CharacterController {

  @Autowired
  private CharacterService characterService;

  @PostMapping("/add")
  ResponseEntity<?> addPinyin(@RequestBody AddPinyinQuery query) {
    Character result = characterService.addPinyin(
        query.getWord(), query.getPinyin(), query.getType()
    );
    return new ResponseEntity<>(new SuccessResponse(result), HttpStatus.OK);
  }

  @GetMapping("/search")
  ResponseEntity<?> searchPinyin(
      @RequestParam String query,
      @RequestParam Integer from,
      @RequestParam Integer size) {
    List<Character> result = characterService.search(query, from, size);
    return new ResponseEntity<>(new SuccessResponse(result), HttpStatus.OK);
  }
}
