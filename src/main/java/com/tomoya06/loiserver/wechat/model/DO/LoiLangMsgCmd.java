package com.tomoya06.loiserver.wechat.model.DO;

import java.security.InvalidParameterException;
import java.util.regex.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Data
@NoArgsConstructor
public class LoiLangMsgCmd {

  private String title;
  private String arg;
  private String arg0;

  private static final Integer ARG_COUNT = 2;
  public static final String PREFIX = "字典";

  private static final String WORD = "字";

  public static final String GENERAL = "多厚";
  public static final String SEARCH = "查字";
  public static final String SHOW = "看字";
  public static final String ADD = "加字";
  public static final String ADD_PINYIN = "加音";
  public static final String DELETE_PINYIN = "删音";
  public static final String DELETE = "删字";

  private static final String REG_CN_WORD = "[\\u4E00-\\u9FA5]+";
  private static final String REG_PINYIN = "[\\w\\W]+";
  private static final String REG_NUMBER = "[0-9]+";

  public static LoiLangMsgCmd parse(String content) {
    if (!content.startsWith(PREFIX)) {
      throw new InvalidParameterException();
    }
    content = content.substring(PREFIX.length());
    String[] args = content.split("\\s+");
    LoiLangMsgCmd loiLangMsgCmd = new LoiLangMsgCmd();
    try {
      switch (args[0]) {
        case GENERAL:
          loiLangMsgCmd.setTitle(GENERAL);
          break;
        case SEARCH:
        case SHOW:
        case DELETE:
          loiLangMsgCmd.setTitle(args[0]);
          Assert.isTrue(Pattern.matches(REG_CN_WORD, args[1]), "");
          loiLangMsgCmd.setArg(args[1]);
          break;
        case ADD:
        case ADD_PINYIN:
          loiLangMsgCmd.setTitle(args[0]);
          Assert.isTrue(Pattern.matches(REG_CN_WORD, args[1]), "");
          loiLangMsgCmd.setArg(args[1]);
          StringBuilder sb = new StringBuilder();
          int wordLength = args[1].length() / WORD.length();
          for (int i = 0; i < wordLength; i += 1) {
            Assert.isTrue(Pattern.matches(REG_PINYIN, args[i + 2]), "");
            sb.append(args[i + 2]);
            sb.append(" ");
          }
          loiLangMsgCmd.setArg0(sb.toString().trim());
          break;
        case DELETE_PINYIN:
          loiLangMsgCmd.setTitle(args[0]);
          Assert.isTrue(Pattern.matches(REG_CN_WORD, args[1]), "");
          Assert.isTrue(Pattern.matches(REG_NUMBER, args[2]), "");
          int idx = Integer.parseInt(args[2]);
          Assert.isTrue(idx > 0, "");
          loiLangMsgCmd.setArg(args[1]);
          loiLangMsgCmd.setArg0(String.format("%d", idx - 1));
          break;
        default:
          throw new InvalidParameterException();
      }
      return loiLangMsgCmd;
    } catch (Exception e) {
      return null;
    }
  }
}
