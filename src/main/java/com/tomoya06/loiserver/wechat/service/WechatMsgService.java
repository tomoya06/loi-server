package com.tomoya06.loiserver.wechat.service;

import com.tomoya06.loiserver.wechat.common.AesException;
import com.tomoya06.loiserver.wechat.common.WXBizMsgCrypt;
import javax.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class WechatMsgService {

  @Getter
  private WXBizMsgCrypt crypt;

  private String token = "NLaWjjjaUEuj8eb3";
  private String encodingAesKey = "qEIVPDSA7rWwYMbdC7wH7zmqPA7OXalhHlqutOJi4S2";
  private String appId = "wx5024bd0942710575";

  @PostConstruct
  private void init() throws AesException {
    crypt = new WXBizMsgCrypt(token, encodingAesKey, appId);
  }
}
