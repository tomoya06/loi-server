package com.tomoya06.loiserver.model.query;

import com.tomoya06.loiserver.model.DO.LoiLangDocument.LangType;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLoiLangQuery {

  private String word;
  private String pinyin;
  private LangType type;

  public boolean isValid() {
    return word != null && pinyin != null && type != null;
  }
}
