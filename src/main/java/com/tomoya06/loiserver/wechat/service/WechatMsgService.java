package com.tomoya06.loiserver.wechat.service;

import com.tomoya06.loiserver.wechat.common.AesException;
import com.tomoya06.loiserver.wechat.common.WXBizMsgCrypt;
import com.tomoya06.loiserver.wechat.model.DO.LoiLangMsgCmd;
import com.tomoya06.loiserver.wechat.model.DTO.InMsgEntity;
import com.tomoya06.loiserver.wechat.model.DTO.OutMsgEntity;
import javax.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WechatMsgService {

  @Getter
  private WXBizMsgCrypt crypt;

  @Value("${wechat.token}")
  private String token;
  @Value("${wechat.encodingAesKey}")
  private String encodingAesKey;
  @Value("${wechat.appId}")
  private String appId;

  @Autowired
  private WechatMsgLoilangService wechatMsgLoilangService;

  @PostConstruct
  private void init() throws AesException {
    crypt = new WXBizMsgCrypt(token, encodingAesKey, appId);
  }

  public OutMsgEntity replyMessage(InMsgEntity inMsgEntity) {
    //获取接收的消息类型
    String msgType = inMsgEntity.getMsgType();

    OutMsgEntity out = null;
    if ("text".equals(msgType)) {
      LoiLangMsgCmd loiLangMsgCmd = LoiLangMsgCmd.parse(inMsgEntity.getContent());
      if (loiLangMsgCmd != null) {
        out = wechatMsgLoilangService.handler(loiLangMsgCmd);
      }
    }
    if (out == null) {
      out = new OutMsgEntity();
      out.setContent("咩啊");
      out.setMsgType("text");
    }
    //把原来的发送方设置为接收方
    out.setToUserName(inMsgEntity.getFromUserName());
    //把原来的接收方设置为发送方
    out.setFromUserName(inMsgEntity.getToUserName());
    //设置消息创建时间
    out.setCreateTime(System.currentTimeMillis());
    return out;
  }
}
