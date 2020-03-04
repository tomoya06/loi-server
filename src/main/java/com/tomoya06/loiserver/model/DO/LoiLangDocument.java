package com.tomoya06.loiserver.model.DO;

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

  @Data
  @NoArgsConstructor
  public static class PinyinDocument {
    private String prefix;
    private String suffix;
    private String pinyin;
  }

  public enum LangType {
    /**
     *
     */
    NORMAL,
    MULTI_PRON,
    SLANG,
    ORAL
  }

  @Id
  private String id;

  @Field("word")
  private String word;

  @Field("pinyins")
  private List<PinyinDocument> pinyins;

  @Field("type")
  private LangType type;
}
