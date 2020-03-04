package com.tomoya06.loiserver.service;

import com.tomoya06.loiserver.model.DO.LoiLangDocument;
import com.tomoya06.loiserver.model.DO.LoiLangDocument.LangType;
import com.tomoya06.loiserver.model.DO.LoiLangDocument.PinyinDocument;
import com.tomoya06.loiserver.model.repo.LoiLangRepository;
import java.util.ArrayList;
import java.util.List;
import javax.management.InstanceAlreadyExistsException;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
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

  public void create(String word, String prefix, String suffix, LangType type) throws InstanceAlreadyExistsException {
    if (loiLangRepository.isWordExists(word)) {
      throw new InstanceAlreadyExistsException();
    }

    LoiLangDocument loiLangDocument = new LoiLangDocument();
    loiLangDocument.setWord(word);

    prefix = prefix.toLowerCase();
    suffix = suffix.toLowerCase();
    PinyinDocument pinyinDocument = new PinyinDocument();
    pinyinDocument.setPrefix(prefix);
    pinyinDocument.setSuffix(suffix);
    pinyinDocument.setPinyin(prefix + suffix);
    var pinyins = new ArrayList<PinyinDocument>();
    pinyins.add(pinyinDocument);
    loiLangDocument.setPinyins(pinyins);
    loiLangDocument.setType(type);

    loiLangRepository.createWord(loiLangDocument);
  }
}
