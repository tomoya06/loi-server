package com.tomoya06.loiserver.loilang.model.DO;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "loilang_recommend")
@Data
@NoArgsConstructor
public class LoiLangRecommendDocument {

  @Id
  private String id;

  private String title;
  private String subtitle;
  private String action;
  private String targetId;
  private int col;
  private String color;
}
