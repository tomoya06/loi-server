package com.tomoya06.loiserver.loilang.service;

import com.tomoya06.loiserver.loilang.model.DO.LoiLangRecommendDocument;
import com.tomoya06.loiserver.loilang.model.repo.LoiLangSubRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoiLangSubService {

  @Autowired
  private LoiLangSubRepository loiLangSubRepository;

  public List<LoiLangRecommendDocument> getRecommend() {
    return loiLangSubRepository.getRecommend();
  }

  public Long getTotalWord() {
    return loiLangSubRepository.getTotalWord();
  }

  public Long getTotalExample() {
    return loiLangSubRepository.getTotalExample();
  }
}
