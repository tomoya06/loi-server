package com.tomoya06.loiserver.loilang.model.repo;

import com.tomoya06.loiserver.loilang.model.DO.LoiLangRecommendDocument;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoiLangSubRepository {

  @Autowired
  private MongoTemplate mongoTemplate;

  public List<LoiLangRecommendDocument> getLatest(Integer size) {
    return mongoTemplate.findAll(LoiLangRecommendDocument.class);
  }
}
