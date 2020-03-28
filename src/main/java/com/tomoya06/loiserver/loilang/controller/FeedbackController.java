package com.tomoya06.loiserver.loilang.controller;

import com.tomoya06.loiserver.common.model.SuccessResponse;
import com.tomoya06.loiserver.loilang.model.query.FeedbackQuery;
import com.tomoya06.loiserver.loilang.service.FeedbackService;
import javax.validation.Valid;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

  @Autowired
  private FeedbackService feedbackService;

  @PostMapping("/send")
  ResponseEntity<?> sendFeedback(@Valid @RequestBody FeedbackQuery query) {
    var result = feedbackService.createFeedback(query);
    return new ResponseEntity<>(new SuccessResponse(result), HttpStatus.OK);
  }
}
