package com.tomoya06.loiserver.loilang.model.DTO;

import com.tomoya06.loiserver.loilang.model.DO.LoiLangDocument;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoiLangGeneralResult {

  private Long total;
  private List<LoiLangDocument> latest;
}
