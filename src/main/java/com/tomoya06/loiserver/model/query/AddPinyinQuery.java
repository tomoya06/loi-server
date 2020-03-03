package com.tomoya06.loiserver.model.query;

import com.tomoya06.loiserver.model.DO.CharacterEntity.CharacterType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddPinyinQuery {

  private String word;
  private String pinyin;
  private CharacterType type;
}
