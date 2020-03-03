package com.tomoya06.loiserver.service;

import com.tomoya06.loiserver.model.DO.CharacterEntity;
import com.tomoya06.loiserver.model.DO.CharacterEntity.CharacterType;
import com.tomoya06.loiserver.model.DTO.Character;
import com.tomoya06.loiserver.model.repo.CharacterPoolRepository;
import com.tomoya06.loiserver.model.repo.CharacterRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {

  @Autowired
  private CharacterRepository characterRepository;

  @Autowired
  private CharacterPoolRepository characterPoolRepository;

  public Map<String, Object> getBrief(Integer size) {
    Long total = characterRepository.count();

    Pageable pageable = PageRequest.of(0, size);
    List<CharacterEntity> entities = characterRepository.findTopByCreateTmExists(pageable);
    List<Character> characterList = entities.stream().map(Character::new).collect(Collectors.toList());

    Map<String, Object> result = new HashMap<>(2);
    result.put("total", total);
    result.put("latest", characterList);

    return result;
  }

  public Character addPinyin(String word, String pinyin, CharacterType type) {
    CharacterEntity newEntity = new CharacterEntity(word, pinyin, type);
    newEntity = characterRepository.saveAndFlush(newEntity);
    return new Character(newEntity);
  }

  public List<Character> search(String word, Integer from, Integer size) {
    Pageable pageable = PageRequest.of(from / size, size);
    List<CharacterEntity> entities = characterRepository.searchWord(word, pageable);
    return entities.stream().map(Character::new).collect(Collectors.toList());
  }

}
