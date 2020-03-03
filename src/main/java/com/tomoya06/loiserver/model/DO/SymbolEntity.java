package com.tomoya06.loiserver.model.DO;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Deprecated
public class SymbolEntity {

  public enum SymbolSystem {
    /**
     * Identify symbol display system
     */
    MZ,
    DH,
    LZ
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String baseSound;
  private String spell;

  @Enumerated(EnumType.STRING)
  private SymbolSystem system;
}
