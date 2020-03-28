package com.tomoya06.loiserver.loilang.model.repo;

import com.tomoya06.loiserver.loilang.model.DO.FeedbackDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FeedbackRepository {

  @Autowired
  private MongoTemplate mongoTemplate;

  private static final String COLL_NAME = "loilang_feedback";

  public FeedbackDocument createFeedback(FeedbackDocument document) {
    return mongoTemplate.save(document, COLL_NAME);
  }
}
