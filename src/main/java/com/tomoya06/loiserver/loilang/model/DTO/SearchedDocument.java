package com.tomoya06.loiserver.loilang.model.DTO;

import com.tomoya06.loiserver.loilang.model.DO.LoiLangDocument.ExampleWord;
import com.tomoya06.loiserver.loilang.model.DO.LoiLangDocument.Pinyin;
import com.tomoya06.loiserver.loilang.model.DO.LoiLangSearchExampleProjection;
import com.tomoya06.loiserver.loilang.model.DO.LoiLangSearchWordProjection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchedDocument {

  public enum SearchedDocumentType {
    //
    WORD,
    PINYIN,
    EG,
  }

  public static final String PINYIN_SEP = "-";
  public static final String EXAMPLE_SEP = "@";

  private String word;
  private String title;
  private List<List<String>> pinyin;
  private List<String> define;
  private SearchedDocumentType type;
  private int score;
  private String targetId;

  public SearchedDocument(LoiLangSearchWordProjection wordProjection) {
    this.word = wordProjection.getWord();
    this.title = wordProjection.getWord();
    this.pinyin = wordProjection.getPinyinList().stream().map(Pinyin::getPinyin).collect(Collectors.toList());
    this.define = wordProjection.getPinyinList().stream().map(Pinyin::getDefine).collect(Collectors.toList());
    this.type = SearchedDocumentType.WORD;
    this.targetId = wordProjection.getId();
  }

  public SearchedDocument(LoiLangSearchExampleProjection exampleProjection) {
    ExampleWord exampleWord = exampleProjection.getExample();
    this.word = exampleWord.getWord();
    this.title = exampleProjection.getWord();
    this.pinyin = exampleWord.getPinyinList();
    this.define = Collections.singletonList(exampleWord.getDefine());
    this.type = SearchedDocumentType.EG;
    this.targetId = String.format("%s%s%d", exampleProjection.getId(), EXAMPLE_SEP, exampleWord.getNid());
  }
}
