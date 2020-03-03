package com.tomoya06.loiserver.model.DTO;

import com.tomoya06.loiserver.model.DO.CharacterEntity;
import com.tomoya06.loiserver.model.DO.CharacterEntity.CharacterType;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class Character {

  private Long id;
  private String word;
  private String pinyin;
  private CharacterType type;

  public Character(CharacterEntity entity) {
    BeanUtils.copyProperties(entity, this);
  }
}
