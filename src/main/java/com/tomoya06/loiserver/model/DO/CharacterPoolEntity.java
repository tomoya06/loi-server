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
public class CharacterPoolEntity {

  public enum CharacterPoolStatus {
    /**
     * Identify character status in pool
     */
    HELP,
    WRONG,
    CLOSED,
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String word;
  private CharacterPoolStatus status;

  private Long targetId;
  private String comment;

  private String closedComment;

  private Timestamp createTm;
  private Timestamp updateTm;

  public CharacterPoolEntity(String word, CharacterPoolStatus status, Long targetId, String comment) {
    this.word = word;
    this.status = status;
    this.targetId = targetId;
    this.comment = comment;
  }
}
