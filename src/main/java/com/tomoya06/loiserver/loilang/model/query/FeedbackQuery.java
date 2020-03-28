package com.tomoya06.loiserver.loilang.model.query;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedbackQuery {

  @NotNull
  private String content;
  @NotNull
  private String author;
  @NotNull
  private String targetId;
}
