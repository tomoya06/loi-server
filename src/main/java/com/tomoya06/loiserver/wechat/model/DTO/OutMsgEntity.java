package com.tomoya06.loiserver.wechat.model.DTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
public class OutMsgEntity {

  protected String FromUserName;
  protected String ToUserName;
  protected Long CreateTime;
  /**
   * 消息类型 text 文本消息 image 图片消息 voice 语音消息 video 视频消息 music 音乐消息 news 图文消息
   */
  protected String MsgType;
  @XmlElementWrapper(name = "Image")
  private String[] MediaId;
  private String Content;
}
