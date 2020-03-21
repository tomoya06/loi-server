package com.tomoya06.loiserver.loilang.model.repo;

import static com.tomoya06.loiserver.loilang.model.util.LoilangUtil.sortWithScores;

import com.tomoya06.loiserver.loilang.model.DO.LoiLangDocument;
import com.tomoya06.loiserver.loilang.model.DTO.SearchedDocument;
import java.lang.Character.UnicodeScript;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class LoiLangRepository {

  @Autowired
  private MongoTemplate mongoTemplate;

  public Long totalCount() {
    Query query = Query.query(Criteria.where("id").exists(true));
    return mongoTemplate.count(query, LoiLangDocument.class);
  }

  public List<LoiLangDocument> getLatest(Integer size) {
    Query query = new Query().with(PageRequest.of(0, size)).with(Sort.by(Direction.DESC, "_id"));
    return mongoTemplate.find(query, LoiLangDocument.class);
  }

  public List<SearchedDocument> searchWord(String word, Boolean isDizzy) {
    List<LoiLangDocument> result;
    if (!isDizzy) {
      result = searchWord(word);
    } else {
      result = searchWordDizzily(word);
    }

    return sortWithScores(result, word);
  }

  private List<LoiLangDocument> searchWord(String word) {
    Query query;
    List<LoiLangDocument> result = new ArrayList<>();
    if (isAllChinese(word)) {
      query = new Query(new Criteria().orOperator(
          Criteria.where("w").is(word),
          Criteria.where("egs.w").regex(".*" + word + ".*")
      ));
      result = mongoTemplate.find(query, LoiLangDocument.class);
    } else if (isAllEnglish(word)) {
      query = new Query(new Criteria().orOperator(
          Criteria.where("defs.pys").is(word),
          Criteria.where("defs.jpys").is(word),
          Criteria.where("egs.pys.jpys").is(word)
      ));
      result = mongoTemplate.find(query, LoiLangDocument.class);
    }
    return result;
  }

  private List<LoiLangDocument> searchWordDizzily(String word) {
    Pattern pattern = Pattern.compile(".*" + Pattern.quote(word) + ".*");

    Query query = new Query(new Criteria().orOperator(
        Criteria.where("w").regex(pattern),
        Criteria.where("egs.w").regex(pattern),
        Criteria.where("defs.def").regex(pattern),
        Criteria.where("egs.def").regex(pattern)
    ));

    return mongoTemplate.find(query, LoiLangDocument.class);
  }

  public LoiLangDocument getWord(String word) {
    Query query = Query.query(Criteria.where("w").is(word));
    return mongoTemplate.findOne(query, LoiLangDocument.class);
  }

  public LoiLangDocument getWordById(String id) {
    Query query = Query.query(Criteria.where("_id").is(id));
    return mongoTemplate.findOne(query, LoiLangDocument.class);
  }

  private static boolean isAllChinese(String string) {
    return string.codePoints().allMatch(
        codepoint -> Character.UnicodeScript.of(codepoint) == UnicodeScript.HAN
    );
  }

  private static boolean isAllEnglish(String string) {
    return Pattern.matches("^[\\w\\s]+$", string);
  }

}
