package com.tomoya06.loiserver.loilang.service;

import com.tomoya06.loiserver.loilang.model.DO.LoiLangRecommendDocument;
import com.tomoya06.loiserver.loilang.model.DTO.LoiLangGeneralResult;
import com.tomoya06.loiserver.loilang.model.repo.LoiLangSubRepository;
import java.util.List;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class LoiLangSubService {

  @Autowired
  private LoiLangSubRepository loiLangSubRepository;

  @Cacheable("loilang:recommend")
  public List<LoiLangRecommendDocument> getRecommend() {
    return loiLangSubRepository.getRecommend();
  }

  @Cacheable("loilang:general")
  public LoiLangGeneralResult getGeneral() {
    Long totalWord = loiLangSubRepository.getTotalWord();
    Long totalExample = loiLangSubRepository.getTotalExample();
    var result = new LoiLangGeneralResult();
    result.setTotalWord(totalWord);
    result.setTotalExample(totalExample);
    return result;
  }
}
