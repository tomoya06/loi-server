package com.tomoya06.loiserver.wechat.service;

import com.tomoya06.loiserver.loilang.model.DO.LoiLangDocument.LangType;
import com.tomoya06.loiserver.loilang.service.LoiLangService;
import com.tomoya06.loiserver.wechat.model.DO.LoiLangMsgCmd;
import com.tomoya06.loiserver.wechat.model.DTO.OutMsgEntity;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.management.InstanceAlreadyExistsException;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class WechatMsgLoilangService {

  @Autowired
  private LoiLangService loiLangService;

  public OutMsgEntity handler(LoiLangMsgCmd msgCmd) {
    if (msgCmd == null) {
      return null;
    }
    switch (msgCmd.getTitle()) {
      case "多厚了":
        return handleInfo();
      case "查字":
        return handleSearchWord(msgCmd);
      case "看字":
        return handleShowWord(msgCmd);
      case "加字":
        return handleAddWord(msgCmd);
      case "加音":
        return handleAddPinyin(msgCmd);
      case "删音":
        return handleDeletePinyin(msgCmd);
      case "删字":
        return handleDeleteWord(msgCmd);
      default:
        return null;
    }
  }

  /**
   * 概览
   *
   * @return
   */
  private OutMsgEntity handleInfo() {
    var result = loiLangService.totalCount();
    String content = String.format("目前黎话字典总共收录了%d个词条", result);
    OutMsgEntity outMsgEntity = new OutMsgEntity();
    outMsgEntity.setContent(content);
    outMsgEntity.setMsgType("text");
    return outMsgEntity;
  }

  /**
   * 查字
   *
   * @param msgCmd
   * @return
   */
  private OutMsgEntity handleSearchWord(LoiLangMsgCmd msgCmd) {
    OutMsgEntity outMsgEntity = new OutMsgEntity();
    if (StringUtils.isEmpty(msgCmd.getArg())) {
      outMsgEntity.setContent("你要找哪个字啊？");
      outMsgEntity.setMsgType("text");
      return outMsgEntity;
    }
    var result = loiLangService.search(msgCmd.getArg());
    String resultMsg = IntStream.range(0, result.size())
        .mapToObj(
            i -> String.format("#%d：%s，共有%d个读音；", i + 1, result.get(i).getWord(), result.get(i).getPinyins().size()))
        .collect(Collectors.joining("\n"));
    String content = String.format("搜到了%d条结果：\n%s\n使用【字典看字】指令查看详情。", result.size(), resultMsg);
    outMsgEntity.setContent(content);
    outMsgEntity.setMsgType("text");
    return outMsgEntity;
  }

  /**
   * 看字
   *
   * @param msgCmd
   * @return
   */
  private OutMsgEntity handleShowWord(LoiLangMsgCmd msgCmd) {
    OutMsgEntity outMsgEntity = new OutMsgEntity();
    if (StringUtils.isEmpty(msgCmd.getArg())) {
      outMsgEntity.setContent("你要看哪个字啊？");
      outMsgEntity.setMsgType("text");
      return outMsgEntity;
    }
    var result = loiLangService.getWord(msgCmd.getArg());
    String resultMsg = String.format("【%s】字共有%d个读音：%s。",
        result.getWord(),
        result.getPinyins().size(),
        String.join("，", result.getPinyins())
    );
    outMsgEntity.setContent(resultMsg);
    outMsgEntity.setMsgType("text");
    return outMsgEntity;
  }

  /**
   * 加字
   *
   * @param msgCmd
   * @return
   */
  private OutMsgEntity handleAddWord(LoiLangMsgCmd msgCmd) {
    OutMsgEntity outMsgEntity = new OutMsgEntity();
    if (StringUtils.isEmpty(msgCmd.getArg())) {
      outMsgEntity.setContent("你要加哪个字啊？");
      outMsgEntity.setMsgType("text");
      return outMsgEntity;
    }
    if (StringUtils.isEmpty(msgCmd.getArg0())) {
      outMsgEntity.setContent("提供一下拼音谢谢");
      outMsgEntity.setMsgType("text");
      return outMsgEntity;
    }
    try {
      var result = loiLangService.create(msgCmd.getArg(), msgCmd.getArg0(), LangType.NORMAL);
      String content = String.format("【%s】加好了。", result.getWord());
      outMsgEntity.setContent(content);
      outMsgEntity.setMsgType("text");
      return outMsgEntity;
    } catch (InstanceAlreadyExistsException e) {
      e.printStackTrace();
      outMsgEntity.setContent("这个字加过啦！");
      outMsgEntity.setMsgType("text");
      return outMsgEntity;
    }
  }

  /**
   * 加读音
   *
   * @param msgCmd
   * @return
   */
  private OutMsgEntity handleAddPinyin(LoiLangMsgCmd msgCmd) {
    OutMsgEntity outMsgEntity = new OutMsgEntity();
    if (StringUtils.isEmpty(msgCmd.getArg())) {
      outMsgEntity.setContent("你要搞哪个字啊？");
      outMsgEntity.setMsgType("text");
      return outMsgEntity;
    }
    if (StringUtils.isEmpty(msgCmd.getArg0())) {
      outMsgEntity.setContent("提供一下拼音谢谢");
      outMsgEntity.setMsgType("text");
      return outMsgEntity;
    }
    try {
      loiLangService.addMultiPron(msgCmd.getArg(), msgCmd.getArg0());
      String content = String.format("【%s】的读音\"%s\"加好了。", msgCmd.getArg(), msgCmd.getArg0());
      outMsgEntity.setContent(content);
      outMsgEntity.setMsgType("text");
      return outMsgEntity;
    } catch (InstanceAlreadyExistsException e) {
      e.printStackTrace();
      outMsgEntity.setContent("这个字加过啦！");
      outMsgEntity.setMsgType("text");
      return outMsgEntity;
    } catch (NullPointerException e) {
      e.printStackTrace();
      outMsgEntity.setContent("这个字还不在字典里。");
      outMsgEntity.setMsgType("text");
      return outMsgEntity;
    }
  }

  /**
   * 删读音
   *
   * @param msgCmd
   * @return
   */
  private OutMsgEntity handleDeletePinyin(LoiLangMsgCmd msgCmd) {
    OutMsgEntity outMsgEntity = new OutMsgEntity();
    if (StringUtils.isEmpty(msgCmd.getArg())) {
      outMsgEntity.setContent("你要找哪个字啊？");
      outMsgEntity.setMsgType("text");
      return outMsgEntity;
    }
    if (StringUtils.isEmpty(msgCmd.getArg0())) {
      outMsgEntity.setContent("提供一下拼音谢谢");
      outMsgEntity.setMsgType("text");
      return outMsgEntity;
    }
    try {
      loiLangService.deletePinyin(msgCmd.getArg(), msgCmd.getArg0());
      String content = String.format("【%s】的读音\"%s\"删掉了。", msgCmd.getArg(), msgCmd.getArg0());
      outMsgEntity.setContent(content);
      outMsgEntity.setMsgType("text");
      return outMsgEntity;
    } catch (NullPointerException e) {
      outMsgEntity.setContent("这条记录还不在字典里");
      outMsgEntity.setMsgType("text");
      return outMsgEntity;
    }
  }

  /**
   * 删字
   *
   * @param msgCmd
   * @return
   */
  private OutMsgEntity handleDeleteWord(LoiLangMsgCmd msgCmd) {
    OutMsgEntity outMsgEntity = new OutMsgEntity();
    if (StringUtils.isEmpty(msgCmd.getArg())) {
      outMsgEntity.setContent("你要找哪个字啊？");
      outMsgEntity.setMsgType("text");
      return outMsgEntity;
    }
    try {
      loiLangService.deleteWord(msgCmd.getArg());
      String content = String.format("【%s】字删掉了。", msgCmd.getArg());
      outMsgEntity.setContent(content);
      outMsgEntity.setMsgType("text");
      return outMsgEntity;
    } catch (NullPointerException e) {
      outMsgEntity.setContent("这个字还不在字典里");
      outMsgEntity.setMsgType("text");
      return outMsgEntity;
    }
  }
}
