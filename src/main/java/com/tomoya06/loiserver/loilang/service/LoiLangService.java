package com.tomoya06.loiserver.loilang.service;

import com.tomoya06.loiserver.loilang.model.DTO.LoiLangDocument;
import com.tomoya06.loiserver.loilang.model.DTO.SearchedDocument;
import com.tomoya06.loiserver.loilang.model.repo.LoiLangRepository;
import com.tomoya06.loiserver.loilang.model.util.CommonUtil;
import com.tomoya06.loiserver.loilang.model.util.LoilangUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class LoiLangService {

  @Autowired
  private LoiLangRepository loiLangRepository;

  @Cacheable("loilang:search")
  public List<SearchedDocument> searchWord(String word, Boolean isSearchExample) {
    List<SearchedDocument> result = new ArrayList<>();
    if (CommonUtil.isAllChinese(word)) {
      if (word.length() == LoilangUtil.CN_CHAR.length() && !isSearchExample) {
        var searchResult = loiLangRepository.searchCharacter(word);
        result = searchResult.stream()
            .map(SearchedDocument::new)
            .collect(Collectors.toList());
      } else {
        var searchResult = loiLangRepository.searchExampleWord(word);
        result = searchResult.stream()
            .map(SearchedDocument::new)
            .collect(Collectors.toList());
      }
    }
    return result;
  }

  @Cacheable("loilang:targetId")
  public LoiLangDocument getWordByTargetId(String targetId) {
    String id = targetId.substring(0, 24);
    var mainDoc = loiLangRepository.getWordById(id);
    return new LoiLangDocument(mainDoc);
  }
}
