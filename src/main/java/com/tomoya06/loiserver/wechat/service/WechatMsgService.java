package com.tomoya06.loiserver.wechat.service;

import com.tomoya06.loiserver.wechat.common.Constant;
import com.tomoya06.loiserver.wechat.model.DO.LoiLangMsgCmd;
import com.tomoya06.loiserver.wechat.model.DTO.InMsgEntity;
import com.tomoya06.loiserver.wechat.model.DTO.OutMsgEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class WechatMsgService {

  @Autowired
  private WechatMsgLoilangService wechatMsgLoilangService;

  public OutMsgEntity replyMessage(InMsgEntity inMsgEntity) {
    String msgType = inMsgEntity.getMsgType();
    String content = inMsgEntity.getContent();
    String fromUserName = inMsgEntity.getFromUserName();

    log.info("rcv msg from #{}", fromUserName);

    OutMsgEntity out = null;
    if (Constant.MSG_TYPE_TEXT.equals(msgType)) {
      if (content.startsWith(LoiLangMsgCmd.PREFIX)) {
        if (content.equals(LoiLangMsgCmd.PREFIX)) {
          out = wechatMsgLoilangService.handleCmdInfo();
        } else {
          LoiLangMsgCmd loiLangMsgCmd = LoiLangMsgCmd.parse(inMsgEntity.getContent());
          if (loiLangMsgCmd != null) {
            out = wechatMsgLoilangService.handler(loiLangMsgCmd);
          }
        }
      }
    }
    if (out == null) {
      out = new OutMsgEntity();
      out.setContent("咩啊");
      out.setMsgType(Constant.MSG_TYPE_TEXT);
    }
    out.setToUserName(inMsgEntity.getFromUserName());
    out.setFromUserName(inMsgEntity.getToUserName());
    out.setCreateTime(System.currentTimeMillis());
    return out;
  }
}
