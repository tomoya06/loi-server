package com.tomoya06.loiserver.loilang.model.DO;

import com.tomoya06.loiserver.loilang.model.query.FeedbackQuery;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "loilang_feedback")
@Data
@NoArgsConstructor
public class FeedbackDocument {

  @Id
  private String id;

  @Field("c")
  private String content;

  @Field("a")
  private String author;

  @Field("t")
  private String targetId;

  @Field("ct")
  private Long createTm;

  public static FeedbackDocument fromQuery(FeedbackQuery query) {
    FeedbackDocument document = new FeedbackDocument();
    BeanUtils.copyProperties(query, document);
    document.setCreateTm(System.currentTimeMillis());
    return document;
  }
}
