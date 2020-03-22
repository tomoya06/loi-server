package com.tomoya06.loiserver.loilang.model.DO;

import com.tomoya06.loiserver.loilang.model.DO.LoiLangDocument.Pinyin;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "loilang_update")
@Data
@NoArgsConstructor
public class LoiLangPinyinProjection {

  @Field("w")
  private String word;

  @Field("defs")
  private List<Pinyin> pinyinList;
}
