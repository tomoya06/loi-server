package com.tomoya06.loiserver.loilang.model.DO;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "loilang_update")
@Data
@NoArgsConstructor
public class LoiLangDocument {

  @Data
  @NoArgsConstructor
  public static class Pinyin {

    @Field("t")
    private String title;
    @Field("rid")
    private String rawId;
    @Field("pys")
    private List<String> pinyin;
    @Field("def")
    private String define;
    @Field("nid")
    private int nid;

    private int score;

    public void addScore(int s) {
      this.setScore(this.getScore() + s);
    }
  }

  @Data
  @NoArgsConstructor
  public static class ExampleWord {

    @Field("w")
    private String word;
    @Field("nid")
    private int nid;
    @Field("pys")
    private List<List<String>> pinyinList;
    @Field("def")
    private String define;
    @Field("jpys")
    private List<String> jointPinyinList;

    private int score;

    public void addScore(int s) {
      this.setScore(this.getScore() + s);
    }
  }

  @Id
  private String id;

  @Field("w")
  private String word;

  @Field("defs")
  private List<Pinyin> pinyinList;

  @Field("egs")
  private List<ExampleWord> examples;

  private int score;

  public void addScore(int s) {
    this.setScore(this.getScore() + s);
  }
}
