package com.tomoya06.loiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LoiServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(LoiServerApplication.class, args);
  }

}
