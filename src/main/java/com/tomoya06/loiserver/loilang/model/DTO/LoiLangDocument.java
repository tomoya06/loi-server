package com.tomoya06.loiserver.loilang.model.DTO;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class LoiLangDocument {

  @Data
  @NoArgsConstructor
  public static class Pinyin {

    private String title;
    private String rawId;
    private String parsedId;
    private String numberId;
    private String pinyin;
    private String define;
  }

  @Data
  @NoArgsConstructor
  public static class ExampleBlock {

    private String title;
    private List<ExampleWord> exampleWordList;
  }

  @Data
  @NoArgsConstructor
  public static class ExampleWord {

    private String word;

    private List<List<String>> pinyinList;

    private String define;
  }

  @Id
  private String id;

  private String word;

  private List<Pinyin> pinyinList;

  private List<ExampleBlock> examples;
}
