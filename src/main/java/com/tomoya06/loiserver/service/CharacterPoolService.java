package com.tomoya06.loiserver.service;

import com.tomoya06.loiserver.model.DO.CharacterPoolEntity;
import com.tomoya06.loiserver.model.DO.CharacterPoolEntity.CharacterPoolStatus;
import com.tomoya06.loiserver.model.DTO.Character;
import com.tomoya06.loiserver.model.DTO.CharacterPool;
import com.tomoya06.loiserver.model.repo.CharacterPoolRepository;
import com.tomoya06.loiserver.model.repo.CharacterRepository;
import java.util.NoSuchElementException;
import javax.management.InstanceAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacterPoolService {

  @Autowired
  private CharacterRepository characterRepository;

  @Autowired
  private CharacterPoolRepository characterPoolRepository;

  @Autowired
  private CharacterService characterService;

  public CharacterPool openIssue(String word, CharacterPoolStatus status, Long targetId, String comment) {
    CharacterPoolEntity entity = new CharacterPoolEntity(word, status, targetId, comment);
    entity = characterPoolRepository.saveAndFlush(entity);
    return new CharacterPool(entity);
  }

  public Character closeIssue(Long id, String correctPinyin)
      throws InstanceAlreadyExistsException, NoSuchElementException {
    CharacterPoolEntity poolEntity = characterPoolRepository.getOne(id);
    if (poolEntity == null) {
      throw new NoSuchElementException();
    }
    if (poolEntity.getStatus().equals(CharacterPoolStatus.CLOSED)) {
      throw new InstanceAlreadyExistsException();
    }
    return updateIssue(poolEntity, correctPinyin);
  }

  private void doCloseIssue(CharacterPoolEntity poolEntity) {
    poolEntity.setStatus(CharacterPoolStatus.CLOSED);
    characterPoolRepository.saveAndFlush(poolEntity);
  }

  private Character updateIssue(CharacterPoolEntity poolEntity, String comment)
      throws InstanceAlreadyExistsException {
    Boolean isExisted = characterRepository.existsByWord(poolEntity.getWord());
    if (isExisted) {
      throw new InstanceAlreadyExistsException();
    }
    Character newChar = characterService.addPinyin(poolEntity.getWord(), comment, null);
    this.doCloseIssue(poolEntity);
    return newChar;
  }

}
