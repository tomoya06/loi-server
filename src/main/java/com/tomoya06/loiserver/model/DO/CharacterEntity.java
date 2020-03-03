package com.tomoya06.loiserver.model.DO;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pengjiahui
 */
@Entity
@Data
@NoArgsConstructor
public class CharacterEntity {

  public enum CharacterType {
    /**
     * Identify entity type
     */
    SINGLE,
    MULTI_PRON,
    WORD,
    SLANG,
    ORAL,
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String word;

  private String pinyin;
  private CharacterType type;

  private Timestamp createTm;
  private Timestamp updateTm;

  public CharacterEntity(String word, String pinyin, CharacterType type) {
    this.word = word;
    this.pinyin = pinyin;
    this.type = type;
  }
}
