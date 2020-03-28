package com.tomoya06.loiserver.loilang.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DetailedDocument {

  private LoiLangDocument main;
  private SearchedDocument highlight;
}
