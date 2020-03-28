package com.tomoya06.loiserver.loilang.model.util;

import com.tomoya06.loiserver.loilang.model.DO.LoiLangDocument;
import com.tomoya06.loiserver.loilang.model.DTO.SearchedDocument;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LoilangUtil {

  public static final String CN_CHAR = "字";
//
//  private static final int WORD_WEIGHT = 100000;
//  private static final int EG_WORD_WEIGHT = 100000;
//  private static final int WORD_DEF_WEIGHT = 1000;
//  private static final int EG_DEF_WEIGHT = 1000;
//  private static final int WORD_PINYIN_WEIGHT = 90000;
//  private static final int EG_PINYIN_WEIGHT = 90000;
//
//  public static List<SearchedDocument> sortWithScores(List<LoiLangDocument> documents, String word) {
//    updateScore(documents, word);
//    List<SearchedDocument> result = new ArrayList<>();
//    documents.forEach(loiLangDocument -> {
//      if (loiLangDocument.getScore() > 0) {
//        result.add(new SearchedDocument(loiLangDocument));
//      }
//      for (int i = 0; i < loiLangDocument.getPinyinList().size(); i += 1) {
//        if (loiLangDocument.getPinyinList().get(i).getScore() > 0) {
//          result.add(new SearchedDocument(loiLangDocument, i));
//        }
//      }
//      if (loiLangDocument.getExamples() != null) {
//        for (int i = 0; i < loiLangDocument.getExamples().size(); i += 1) {
//          if (loiLangDocument.getExamples().get(i).getScore() > 0) {
//            result.add(new SearchedDocument(loiLangDocument, i, loiLangDocument.getExamples().get(i)));
//          }
//        }
//      }
//    });
//    result.sort((a, b) -> b.getScore() - a.getScore());
//    return result;
//  }
//
//  private static void updateScore(List<LoiLangDocument> documents, String word) {
//    documents.replaceAll(loiLangDocument -> {
//      AtomicInteger docScore = new AtomicInteger();
//      docScore.addAndGet(calcScore(loiLangDocument.getWord(), word, WORD_WEIGHT));
//
//      // 各个拼音评分
//      loiLangDocument.getPinyinList().replaceAll(pinyin -> {
//        int pinyinScore = 0;
//        pinyinScore += calcScore(pinyin.getDefine(), word, WORD_DEF_WEIGHT);
//
//        for (String s : pinyin.getPinyin()) {
//          if (s.startsWith(word)) {
//            pinyinScore += WORD_PINYIN_WEIGHT;
//          }
//        }
//
//        pinyin.addScore(pinyinScore);
//        docScore.addAndGet(pinyinScore);
//        return pinyin;
//      });
//
//      // 各个组词评分
//      if (loiLangDocument.getExamples() != null) {
//        loiLangDocument.getExamples().replaceAll(exampleWord -> {
//          int egScore = 0;
//          egScore += calcScore(exampleWord.getWord(), word, EG_WORD_WEIGHT);
//          egScore += calcScore(exampleWord.getDefine(), word, EG_DEF_WEIGHT);
//
//          for (String s : exampleWord.getJointPinyinList()) {
//            if (s.startsWith(word)) {
//              egScore += EG_PINYIN_WEIGHT;
//            }
//          }
//
//          exampleWord.addScore(egScore);
//          docScore.addAndGet(egScore);
//          return exampleWord;
//        });
//      }
//
//      loiLangDocument.addScore(docScore.get());
//      return loiLangDocument;
//    });
//  }

  private static int calcScore(String string, String target, int weight) {
    int al = string.length();
    int ml = string.replaceAll(target, "").length();
    double t = (double) (al - ml) / al;
    return (int) (t * weight);
  }
}
