package com.tomoya06.loiserver.loilang.model.repo;

import com.tomoya06.loiserver.loilang.model.DO.LoiLangDocument;
import com.tomoya06.loiserver.loilang.model.DO.LoiLangSearchExampleProjection;
import com.tomoya06.loiserver.loilang.model.DO.LoiLangSearchWordProjection;
import java.util.List;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class LoiLangRepository {

  @Autowired
  private MongoTemplate mongoTemplate;

  private static final String COLL_NAME = "loilang_update";

  public List<LoiLangSearchWordProjection> searchCharacter(String word) {
    Aggregation aggregation = Aggregation.newAggregation(
        Aggregation.match(Criteria.where("w").is(word)),
        Aggregation.project("w", "defs")
    );
    var results = mongoTemplate.aggregate(aggregation, COLL_NAME, LoiLangSearchWordProjection.class);
    return results.getMappedResults();
  }

  public List<LoiLangSearchExampleProjection> searchExampleWord(String word) {
    String wordRegex = String.format(".*%s.*", word);

    Aggregation aggregation = Aggregation.newAggregation(
        Aggregation.unwind("egs"),
        Aggregation.match(Criteria.where("egs.w").regex(wordRegex)),
        Aggregation.project("egs", "w")
    );

    var results = mongoTemplate.aggregate(aggregation, COLL_NAME, LoiLangSearchExampleProjection.class);
    return results.getMappedResults();
  }

  public LoiLangDocument getWord(String word) {
    Query query = Query.query(Criteria.where("w").is(word));
    return mongoTemplate.findOne(query, LoiLangDocument.class);
  }

  public LoiLangDocument getWordById(String id) {
    Query query = Query.query(Criteria.where("_id").is(id));
    return mongoTemplate.findOne(query, LoiLangDocument.class);
  }
}
