package com.tomoya06.loiserver.loilang.model.util;

import java.lang.Character.UnicodeScript;
import java.util.regex.Pattern;

public class CommonUtil {

  public static boolean isAllChinese(String string) {
    return string.codePoints().allMatch(
        codepoint -> Character.UnicodeScript.of(codepoint) == UnicodeScript.HAN
    );
  }

  public static boolean isAllEnglish(String string) {
    return Pattern.matches("^[\\w\\s]+$", string);
  }
}
