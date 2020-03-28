package com.tomoya06.loiserver.loilang.model.DO;

import com.tomoya06.loiserver.loilang.model.DO.LoiLangDocument.ExampleWord;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "loilang_update")
@Data
@NoArgsConstructor
public class LoiLangSearchExampleProjection {

  @Id
  private String id;

  @Field("w")
  private String word;

  @Field("egs")
  private ExampleWord example;
}
