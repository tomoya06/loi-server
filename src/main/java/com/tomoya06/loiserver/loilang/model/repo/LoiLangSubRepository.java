package com.tomoya06.loiserver.loilang.model.repo;

import com.tomoya06.loiserver.loilang.model.DO.LoiLangDocument;
import com.tomoya06.loiserver.loilang.model.DO.LoiLangGeneralProjection;
import com.tomoya06.loiserver.loilang.model.DO.LoiLangRecommendDocument;
import java.util.List;
import java.util.stream.Collectors;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class LoiLangSubRepository {

  @Autowired
  private MongoTemplate mongoTemplate;

  private static final String COLL_NAME = "loilang_update";


  public List<LoiLangRecommendDocument> getRecommend() {
    return mongoTemplate.findAll(LoiLangRecommendDocument.class);
  }

  public Long getTotalWord() {
    return mongoTemplate.count(Query.query(Criteria.where("w").exists(true)), LoiLangDocument.class);
  }

  public Long getTotalExample() {
    Aggregation aggregation = Aggregation.newAggregation(
        Aggregation.match(Criteria.where("egs").exists(true)),
        Aggregation.project().and("egs").size().as("totalExample")
    );
    var results = mongoTemplate.aggregate(aggregation, COLL_NAME, LoiLangGeneralProjection.class);
    var counts = results.getMappedResults().stream()
        .map(LoiLangGeneralProjection::getTotalExample)
        .collect(Collectors.toList());
    return (Long) (long) counts.stream().mapToInt(Integer::intValue).sum();
  }
}
