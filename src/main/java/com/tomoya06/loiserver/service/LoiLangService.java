package com.tomoya06.loiserver.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.tomoya06.loiserver.model.DO.LoiLangDocument;
import com.tomoya06.loiserver.model.DO.LoiLangDocument.LangType;
import com.tomoya06.loiserver.model.repo.LoiLangRepository;
import java.util.Collections;
import java.util.List;
import javax.management.InstanceAlreadyExistsException;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class LoiLangService {

  @Autowired
  private LoiLangRepository loiLangRepository;

  public List<LoiLangDocument> search(String word) {
    return loiLangRepository.searchWord(word);
  }

  public LoiLangDocument getWord(String word) {
    return loiLangRepository.getWord(word);
  }

  public LoiLangDocument create(String word, String pinyin, LangType type)
      throws InstanceAlreadyExistsException {
    if (loiLangRepository.isWordExists(word)) {
      throw new InstanceAlreadyExistsException();
    }

    pinyin = pinyin.toLowerCase();
    LoiLangDocument loiLangDocument = new LoiLangDocument();
    loiLangDocument.setWord(word);
    loiLangDocument.setPinyins(Collections.singletonList(pinyin));
    loiLangDocument.setType(type);

    return loiLangRepository.createWord(loiLangDocument);
  }

  public UpdateResult addMultiPron(String word, String pinyin)
      throws NullPointerException, InstanceAlreadyExistsException {
    var wordDoc = loiLangRepository.getWord(word);

    if (wordDoc == null) {
      throw new NullPointerException();
    }

    pinyin = pinyin.toLowerCase();
    if (wordDoc.getPinyins().contains(pinyin)) {
      throw new InstanceAlreadyExistsException();
    }

    Update update = new Update();
    update.addToSet("pinyins", pinyin);

    return loiLangRepository.updateWord(word, update);
  }

  public UpdateResult deletePinyin(String word, String oldPinyin) {
    var wordDoc = loiLangRepository.getWord(word);
    oldPinyin = oldPinyin.toLowerCase();

    if (wordDoc == null || !wordDoc.getPinyins().contains(oldPinyin)) {
      throw new NullPointerException();
    }

    Update update = new Update();
    update.pull("pinyins", oldPinyin);
    return loiLangRepository.updateWord(word, update);
  }

  public DeleteResult deleteWord(String word) {
    if (!loiLangRepository.isWordExists(word)) {
      throw new NullPointerException();
    }
    return loiLangRepository.removeWord(word);
  }
}
