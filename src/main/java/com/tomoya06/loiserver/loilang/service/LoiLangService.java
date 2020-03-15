package com.tomoya06.loiserver.loilang.service;

import com.tomoya06.loiserver.loilang.model.DTO.LoiLangDocument;
import com.tomoya06.loiserver.loilang.model.DTO.LoiLangGeneralResult;
import com.tomoya06.loiserver.loilang.model.DTO.SearchedDocument;
import com.tomoya06.loiserver.loilang.model.repo.LoiLangRepository;
import java.util.List;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoiLangService {

  @Autowired
  private LoiLangRepository loiLangRepository;

  public LoiLangGeneralResult getGeneral() {
    LoiLangGeneralResult result = new LoiLangGeneralResult();
    Long total = loiLangRepository.totalCount();
    var list = loiLangRepository.getLatest(10);
    result.setLatest(LoiLangDocument.fromList(list));
    result.setTotal(total);
    return result;
  }

  public List<SearchedDocument> search(String word, Boolean isDizzy) {
    return loiLangRepository.searchWord(word, isDizzy);
  }

  public LoiLangDocument getWord(String word) {
    var result = loiLangRepository.getWord(word);
    return new LoiLangDocument(result);
  }
}
