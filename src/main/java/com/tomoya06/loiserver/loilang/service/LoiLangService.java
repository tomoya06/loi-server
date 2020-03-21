package com.tomoya06.loiserver.loilang.service;

import com.tomoya06.loiserver.loilang.model.DTO.DetailedDocument;
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

  public DetailedDocument getWordByTargetId(String targetId) {
    String id;
    Integer egId = null, pyId = null;
    if (targetId.indexOf(SearchedDocument.EXAMPLE_SEP) > 0) {
      id = targetId.substring(0, targetId.indexOf(SearchedDocument.EXAMPLE_SEP));
      egId = Integer.parseInt(targetId.substring(targetId.indexOf(SearchedDocument.EXAMPLE_SEP) + 1));
    } else if (targetId.indexOf(SearchedDocument.PINYIN_SEP) > 0) {
      id = targetId.substring(0, targetId.indexOf(SearchedDocument.PINYIN_SEP));
      pyId = Integer.parseInt(targetId.substring(targetId.indexOf(SearchedDocument.PINYIN_SEP) + 1));
    } else {
      id = targetId;
    }
    var mainDoc = loiLangRepository.getWordById(id);
    var result = new DetailedDocument();
    result.setMain(new LoiLangDocument(mainDoc));
    if (egId != null) {
      SearchedDocument hDoc = new SearchedDocument(mainDoc, egId);
      result.setHighlight(hDoc);
    } else if (pyId != null) {
      SearchedDocument hDoc = new SearchedDocument(mainDoc, pyId, null);
      result.setHighlight(hDoc);
    }
    return result;
  }
}
