package com.tomoya06.loiserver.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddPronQuery {

  private String word;
  private String pinyin;

  public boolean isValid() {
    return word != null && pinyin != null;
  }
}
