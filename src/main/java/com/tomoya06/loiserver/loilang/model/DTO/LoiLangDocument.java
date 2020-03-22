package com.tomoya06.loiserver.loilang.model.DTO;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class LoiLangDocument {

  @Data
  @NoArgsConstructor
  public static class Pinyin {

    private int nid;
    private String title;
    private String rawId;
    private List<String> pinyin;
    private String define;
  }

  @Data
  @NoArgsConstructor
  public static class ExampleWord {

    private int nid;
    private String word;
    private List<List<String>> pinyinList;
    private String define;
  }

  private String word;
  private List<Pinyin> pinyinList;
  private List<ExampleWord> examples;

  public LoiLangDocument(com.tomoya06.loiserver.loilang.model.DO.LoiLangDocument loiLangDocument) {
    BeanUtils.copyProperties(loiLangDocument, this);
  }

  public static List<LoiLangDocument> fromList(List<com.tomoya06.loiserver.loilang.model.DO.LoiLangDocument> list) {
    return list.stream().map(LoiLangDocument::new).collect(Collectors.toList());
  }
}
