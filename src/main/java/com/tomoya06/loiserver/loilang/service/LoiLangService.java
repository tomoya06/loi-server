package com.tomoya06.loiserver.loilang.service;

import com.tomoya06.loiserver.loilang.model.DO.LoiLangDocument;
import com.tomoya06.loiserver.loilang.model.DTO.LoiLangGeneralResult;
import com.tomoya06.loiserver.loilang.model.repo.LoiLangRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoiLangService {

  @Autowired
  private LoiLangRepository loiLangRepository;

  public LoiLangGeneralResult getGeneral() {
    LoiLangGeneralResult result = new LoiLangGeneralResult();
    Long total = loiLangRepository.totalCount();
    List<LoiLangDocument> list = loiLangRepository.getLatest(10);
    result.setLatest(list);
    result.setTotal(total);
    return result;
  }

  public List<LoiLangDocument> search(String word, Boolean isDizzy) {
    return loiLangRepository.searchWord(word, isDizzy);
  }

  public LoiLangDocument getWord(String word) {
    return loiLangRepository.getWord(word);
  }
}
