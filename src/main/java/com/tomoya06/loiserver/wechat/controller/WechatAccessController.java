package com.tomoya06.loiserver.wechat.controller;

import com.tomoya06.loiserver.wechat.common.AesException;
import com.tomoya06.loiserver.wechat.model.DTO.OutMsgEntity;
import com.tomoya06.loiserver.wechat.model.query.InMsgEntity;
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
  ResponseEntity<?> startAccess(
      @RequestParam String signature,
      @RequestParam String timestamp,
      @RequestParam String nonce,
      @RequestParam String echostr) throws AesException {

    return new ResponseEntity<>(echostr, HttpStatus.OK);
  }

  @ResponseBody
  @PostMapping(value = "/start", produces = {MediaType.TEXT_XML_VALUE})
  ResponseEntity<?> receiveMessage(@RequestBody InMsgEntity msg) {
    //创建消息响应对象
    OutMsgEntity out = new OutMsgEntity();
    //把原来的发送方设置为接收方
    out.setToUserName(msg.getFromUserName());
    //把原来的接收方设置为发送方
    out.setFromUserName(msg.getToUserName());
    //获取接收的消息类型
    String msgType = msg.getMsgType();
    //设置消息的响应类型
    out.setMsgType(msgType);
    //设置消息创建时间
    out.setCreateTime(System.currentTimeMillis());
    //根据类型设置不同的消息数据
    if ("text".equals(msgType)) {
      out.setContent(msg.getContent());
    } else if ("image".equals(msgType)) {
      out.setMediaId(new String[]{msg.getMediaId()});
    }
    return new ResponseEntity<>(out, HttpStatus.OK);
  }
}
