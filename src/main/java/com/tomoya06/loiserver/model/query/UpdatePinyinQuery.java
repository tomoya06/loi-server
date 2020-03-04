package com.tomoya06.loiserver.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePinyinQuery {

  private String word;
  private String pinyin;
  private String old;

  public boolean isValid() {
    return word != null && pinyin != null && old != null;
  }
}
