package com.tomoya06.loiserver.model.DTO;

import com.tomoya06.loiserver.model.DO.CharacterPoolEntity;
import com.tomoya06.loiserver.model.DO.CharacterPoolEntity.CharacterPoolStatus;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author pengjiahui
 */
@Data
public class CharacterPool {

  private Long id;
  private String word;
  private CharacterPoolStatus status;
  private Long targetId;

  public CharacterPool(CharacterPoolEntity entity) {
    BeanUtils.copyProperties(entity, this);
  }
}
