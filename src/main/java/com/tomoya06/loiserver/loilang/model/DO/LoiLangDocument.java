package com.tomoya06.loiserver.loilang.model.DO;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "loilang")
@Data
@NoArgsConstructor
public class LoiLangDocument {

  public enum LangType {
    /**
     *
     */
    NORMAL,
    SLANG,
    ORAL
  }

  @Id
  private String id;

  @Field("word")
  private String word;

  @Field("pinyins")
  private List<String> pinyins;

  @Field("type")
  private LangType type;
}
