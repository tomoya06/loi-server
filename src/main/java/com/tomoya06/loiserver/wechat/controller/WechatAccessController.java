package com.tomoya06.loiserver.wechat.controller;

import com.tomoya06.loiserver.wechat.common.AesException;
import com.tomoya06.loiserver.wechat.model.DTO.OutMsgEntity;
import com.tomoya06.loiserver.wechat.model.DTO.InMsgEntity;
import com.tomoya06.loiserver.wechat.service.WechatMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wechat/access")
public class WechatAccessController {

  @Autowired
  private WechatMsgService wechatMsgService;

  @GetMapping("/start")
  ResponseEntity<?> startAccess(@RequestParam String echostr) throws AesException {
    return new ResponseEntity<>(echostr, HttpStatus.OK);
  }

  @ResponseBody
  @PostMapping(value = "/start", produces = {MediaType.TEXT_XML_VALUE})
  ResponseEntity<?> receiveMessage(@RequestBody InMsgEntity msg) {
    OutMsgEntity out = wechatMsgService.replyMessage(msg);
    return new ResponseEntity<>(out, HttpStatus.OK);
  }
}
