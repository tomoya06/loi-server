package com.tomoya06.loiserver.loilang.model.repo;

import com.tomoya06.loiserver.loilang.model.DO.LoiLangDocument;
import java.lang.Character.UnicodeScript;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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

  public List<LoiLangDocument> searchWord(String word, Boolean isDizzy) {
    List<LoiLangDocument> result;
    if (!isDizzy) {
      result = searchWord(word);
    } else {
      result = searchWordDizzily(word);
    }

    updateScores(result, word, isDizzy);
    return result;
  }

  private List<LoiLangDocument> searchWord(String word) {
    Query query;
    List<LoiLangDocument> result = new ArrayList<>();
    if (isAllChinese(word)) {
      query = new Query(new Criteria().orOperator(
          Criteria.where("w").is(word),
          Criteria.where("egs.w").is(word)
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

  private static boolean isAllChinese(String string) {
    return string.codePoints().allMatch(
        codepoint -> Character.UnicodeScript.of(codepoint) == UnicodeScript.HAN
    );
  }

  private static boolean isAllEnglish(String string) {
    return Pattern.matches("^[\\w\\s]+$", string);
  }

  private void updateScores(List<LoiLangDocument> documents, String word, Boolean isDizzy) {
    documents.replaceAll(loiLangDocument -> {
      if (loiLangDocument.getWord().equals(word)) {
        loiLangDocument.addScore(90000);
      }
      loiLangDocument.getPinyinList().forEach(pinyin -> {
        if (pinyin.getPinyin().contains(word)) {
          loiLangDocument.addScore(10000);
        }
      });
      if (loiLangDocument.getExamples() != null) {
        loiLangDocument.getExamples().replaceAll(exampleWord -> {
          if (exampleWord.getWord().contains(word)) {
            int t = exampleWord.getWord().length() - exampleWord.getWord().replaceAll(word, "").length();
            loiLangDocument.addScore(1000 * t);
            exampleWord.addScore(1000 * t);
          }
          if (exampleWord.getJointPinyinList().contains(word)) {
            loiLangDocument.addScore(1000);
            exampleWord.addScore(1000);
          }
          return exampleWord;
        });
        loiLangDocument.getExamples().sort((a, b) -> b.getScore() - a.getScore());
        if (!isDizzy) {
          loiLangDocument.setExamples(loiLangDocument.getExamples()
              .stream()
              .filter(exampleWord -> exampleWord.getScore() > 0)
              .collect(Collectors.toList())
          );
        }
      }
      return loiLangDocument;
    });
    documents.sort((a, b) -> b.getScore() - a.getScore());
  }
}
