package com.tomoya06.loiserver.loilang.model.DTO;

import com.tomoya06.loiserver.loilang.model.DO.LoiLangDocument;
import com.tomoya06.loiserver.loilang.model.DO.LoiLangDocument.Pinyin;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.var;

@Data
@NoArgsConstructor
public class SearchedDocument {

  public enum SearchedDocumentType {
    //
    WORD,
    PINYIN,
    EG,
  }

  private String word;
  private String title;
  private List<List<String>> pinyin;
  private List<String> define;
  private SearchedDocumentType type;
  private int score;

  private String targetId;

  public SearchedDocument(LoiLangDocument document) {
    this.word = document.getWord();
    this.title = document.getWord();
    this.pinyin = document.getPinyinList().stream().map(Pinyin::getPinyin).collect(Collectors.toList());
    this.define = document.getPinyinList().stream().map(Pinyin::getDefine).collect(Collectors.toList());
    this.targetId = document.getId();
    this.score = document.getScore();
    this.type = SearchedDocumentType.WORD;
  }

  public SearchedDocument(LoiLangDocument document, Integer pinyinIdx) {
    this.word = document.getWord();
    var pinyin = document.getPinyinList().get(pinyinIdx);
    this.title = document.getPinyinList().get(pinyinIdx).getTitle();
    this.pinyin = Collections.singletonList(pinyin.getPinyin());
    this.define = Collections.singletonList(pinyin.getDefine());
    this.targetId = String.format("%s-%s", document.getId(), pinyinIdx);
    this.score = pinyin.getScore();
    this.type = SearchedDocumentType.PINYIN;
  }

  public SearchedDocument(LoiLangDocument loiLangDocument, LoiLangDocument.ExampleWord exampleWord) {
    this.word = exampleWord.getWord();
    this.title = exampleWord.getWord();
    this.pinyin = exampleWord.getPinyinList();
    this.define = Collections.singletonList(exampleWord.getDefine());
    this.targetId = String.format("%s@%s", loiLangDocument.getId(), exampleWord.getWord());
    this.score = exampleWord.getScore();
    this.type = SearchedDocumentType.EG;
  }
}
