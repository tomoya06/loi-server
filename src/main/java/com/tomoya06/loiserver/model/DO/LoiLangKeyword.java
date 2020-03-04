package com.tomoya06.loiserver.model.DO;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class LoiLangKeyword {

  @Data
  public static class Prefix {

    private String prefix;
    private String eg;
  }

  @Data
  public static class Suffix {

    private String suffix;
    private String eg;
  }

  private Gson gson = new Gson();

  @Getter
  private List<Prefix> prefixes;
  @Getter
  private List<Suffix> suffixes;

  @PostConstruct
  private void init() {
    try {
      File file = ResourceUtils.getFile("classpath:static/loilang_pinyin.json");
      String content = new String(Files.readAllBytes(file.toPath()));
      JsonObject jsonObject = gson.fromJson(content, JsonObject.class);
      JsonArray prefixesArray = jsonObject.getAsJsonArray("prefixes");
      prefixes = Arrays.asList(gson.fromJson(prefixesArray, Prefix[].class));

      JsonArray suffixArray = jsonObject.getAsJsonArray("suffixes");
      suffixes = Arrays.asList(gson.fromJson(suffixArray, Suffix[].class));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
