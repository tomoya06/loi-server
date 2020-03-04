package com.tomoya06.loiserver.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveWordQuery {

  private String word;

  public boolean isValid() {
    return word != null;
  }
}
