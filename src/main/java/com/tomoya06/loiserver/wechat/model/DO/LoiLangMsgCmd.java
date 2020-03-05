package com.tomoya06.loiserver.wechat.model.DO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Data
@NoArgsConstructor
public class LoiLangMsgCmd {

  private String title;
  private String arg;
  private String arg0;

  private static final Integer ARG_COUNT = 2;
  private static final String REGEXP = "^字典(查字|看字|加字|加音|删音|删字|多厚了)(\\s+[\\u4E00-\\u9FA5]+)?(\\s+\\w+)?\\s*$";

  public static LoiLangMsgCmd parse(String content) {
    Pattern pattern = Pattern.compile(REGEXP);
    Matcher matcher = pattern.matcher(content);
    if (matcher.find()) {
      LoiLangMsgCmd loiLangMsgCmd = new LoiLangMsgCmd();
      loiLangMsgCmd.setTitle(matcher.group(1));
      loiLangMsgCmd.setArg(StringUtils.trimAllWhitespace(matcher.group(2)));
      loiLangMsgCmd.setArg0(StringUtils.trimAllWhitespace(matcher.group(3)));
      return loiLangMsgCmd;
    }
    return null;
  }
}
