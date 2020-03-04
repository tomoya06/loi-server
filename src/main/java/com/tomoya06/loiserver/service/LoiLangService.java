package com.tomoya06.loiserver.service;

import com.tomoya06.loiserver.model.DO.LoiLangDocument;
import java.util.List;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class LoiLangService {

  @Autowired
  private MongoTemplate mongoTemplate;

  public List<LoiLangDocument> test() {
    Query query = Query.query(Criteria.where("word").is("陈"));
    List<LoiLangDocument> result = mongoTemplate.find(query, LoiLangDocument.class);
    return result;
  }
}
