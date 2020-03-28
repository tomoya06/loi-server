package com.tomoya06.loiserver.loilang.service;

import com.tomoya06.loiserver.loilang.model.DO.FeedbackDocument;
import com.tomoya06.loiserver.loilang.model.query.FeedbackQuery;
import com.tomoya06.loiserver.loilang.model.repo.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

  @Autowired
  private FeedbackRepository feedbackRepository;

  public FeedbackDocument createFeedback(FeedbackQuery query) {
    FeedbackDocument document = FeedbackDocument.fromQuery(query);
    return feedbackRepository.createFeedback(document);
  }
}
