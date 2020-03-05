package com.tomoya06.loiserver.loilang.model.repo;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.tomoya06.loiserver.loilang.model.DO.LoiLangDocument;
import java.util.List;
import java.util.regex.Pattern;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class LoiLangRepository {

  @Autowired
  private MongoTemplate mongoTemplate;

  public Long totalCount() {
    Query query = Query.query(Criteria.where("id").exists(true));
    return mongoTemplate.count(query, LoiLangDocument.class);
  }

  public List<LoiLangDocument> searchWord(String word) {
    Pattern pattern = Pattern.compile("^" + Pattern.quote(word), Pattern.CASE_INSENSITIVE);
    Query query = Query.query(Criteria.where("word").regex(pattern));
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

  public LoiLangDocument createWord(LoiLangDocument document) {
    return mongoTemplate.insert(document);
  }

  public UpdateResult updateWord(String word, Update update) {
    Query query = Query.query(Criteria.where("word").is(word));
    return mongoTemplate.updateFirst(query, update, LoiLangDocument.class);
  }

  public DeleteResult removeWord(String word) {
    Query query = Query.query(Criteria.where("word").is(word));
    return mongoTemplate.remove(query, LoiLangDocument.class);
  }
}
