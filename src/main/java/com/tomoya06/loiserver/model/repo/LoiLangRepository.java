package com.tomoya06.loiserver.model.repo;

import com.tomoya06.loiserver.model.DO.LoiLangDocument;
import java.util.List;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class LoiLangRepository {

  @Autowired
  private MongoTemplate mongoTemplate;

  public List<LoiLangDocument> searchWord(String word) {
    Query query = Query.query(Criteria.where("word").regex("/^" + word + "/"));
    return mongoTemplate.find(query, LoiLangDocument.class);
  }

  public LoiLangDocument getWord(String word) {
    Query query = Query.query(Criteria.where("word").is(word));
    return mongoTemplate.findOne(query, LoiLangDocument.class);
  }

  public boolean isWordExists(String word) {
    Query query = Query.query(Criteria.where("word").is(word));
    var doc = mongoTemplate.findOne(query, LoiLangDocument.class);
    return doc != null;
  }

  public void createWord(LoiLangDocument document) {
    mongoTemplate.insert(document);
  }
}
