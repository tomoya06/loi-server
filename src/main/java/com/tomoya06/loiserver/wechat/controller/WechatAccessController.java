package com.tomoya06.loiserver.wechat.controller;

import com.tomoya06.loiserver.wechat.common.AesException;
import com.tomoya06.loiserver.wechat.service.WechatMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/wechat/access")
public class WechatAccessController {

  @Autowired
  private WechatMsgService wechatMsgService;

  @GetMapping("/start")
  ResponseEntity<?> startAccess(
      @RequestParam String signature,
      @RequestParam String timestamp,
      @RequestParam String nonce,
      @RequestParam String echostr) throws AesException {

    return new ResponseEntity<>(echostr, HttpStatus.OK);
  }
}
