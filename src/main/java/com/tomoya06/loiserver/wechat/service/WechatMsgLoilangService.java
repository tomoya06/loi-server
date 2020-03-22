package com.tomoya06.loiserver.wechat.service;

import static com.tomoya06.loiserver.wechat.model.DO.LoiLangMsgCmd.GENERAL;
import static com.tomoya06.loiserver.wechat.model.DO.LoiLangMsgCmd.SEARCH;
import static com.tomoya06.loiserver.wechat.model.DO.LoiLangMsgCmd.SHOW;

import com.tomoya06.loiserver.loilang.service.LoiLangService;
import com.tomoya06.loiserver.wechat.common.Constant;
import com.tomoya06.loiserver.wechat.model.DO.LoiLangMsgCmd;
import com.tomoya06.loiserver.wechat.model.DTO.OutMsgEntity;
import java.security.InvalidParameterException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WechatMsgLoilangService {

  @Autowired
  private LoiLangService loiLangService;

  @Value("${wechat.bot.loilang.status}")
  private String loiLangStatus;

  @Value("${wechat.bot.loilang.title}")
  private String botTitle;

  public OutMsgEntity handler(LoiLangMsgCmd msgCmd) {
    if (msgCmd == null) {
      return null;
    }
    try {
      switch (msgCmd.getTitle()) {
        case GENERAL:
          return handleInfo();
        case SEARCH:
          return handleSearchWord(msgCmd);
        case SHOW:
          return handleShowWord(msgCmd);
//        case ADD:
//          return handleAddWord(msgCmd);
//        case ADD_PINYIN:
//          return handleAddPinyin(msgCmd);
//        case DELETE_PINYIN:
//          return handleDeletePinyin(msgCmd);
//        case DELETE:
//          return handleDeleteWord(msgCmd);
        default:
          return null;
      }
    } catch (InvalidParameterException e) {
      OutMsgEntity outMsgEntity = new OutMsgEntity();
      outMsgEntity.setContent("换个参数试试");
      outMsgEntity.setMsgType(Constant.MSG_TYPE_TEXT);
      return outMsgEntity;
    } catch (NullPointerException e) {
      OutMsgEntity outMsgEntity = new OutMsgEntity();
      outMsgEntity.setContent("你要的东西跑到别的字典了");
      outMsgEntity.setMsgType(Constant.MSG_TYPE_TEXT);
      return outMsgEntity;
    } catch (Exception e) {
      OutMsgEntity outMsgEntity = new OutMsgEntity();
      outMsgEntity.setContent("你在试探我的底线，而且成功了（草\n但我还活着🙃");
      outMsgEntity.setMsgType(Constant.MSG_TYPE_TEXT);
      return outMsgEntity;
    }
  }

  public OutMsgEntity handleCmdInfo() {
    OutMsgEntity outMsgEntity = new OutMsgEntity();
    outMsgEntity.setMsgType(Constant.MSG_TYPE_TEXT);

    String content = botTitle + "bot"
        + "\n当前状态：" + loiLangStatus
        + "\n目前所有可用指令："
        + "\n“字典多厚”：查看字典概况"
        + "\n“字典查字 我”：搜索以“我”开头的词条"
        + "\n“字典看字 我”：查看“我”词条"
        + "\n[NEY]“字典缺字 哈”：上报字典尚未录入的词条";
    outMsgEntity.setContent(content);
    return outMsgEntity;
  }

  /**
   * 概览
   *
   * @return
   */
  private OutMsgEntity handleInfo() {
    var result = loiLangService.getGeneral();
    String latestWord = result.getLatest().stream()
        .map(doc -> String.format("「%s」", doc.getWord()))
        .collect(Collectors.joining("，"));
    String content = String.format("目前%s总共收录了%d个词条。"
        + "\n最近加入的词条有："
        + "\n%s", botTitle, result.getTotal(), latestWord);
    OutMsgEntity outMsgEntity = new OutMsgEntity();
    outMsgEntity.setContent(content);
    outMsgEntity.setMsgType(Constant.MSG_TYPE_TEXT);
    return outMsgEntity;
  }

  /**
   * 查字
   *
   * @param msgCmd
   * @return
   */
  private OutMsgEntity handleSearchWord(LoiLangMsgCmd msgCmd) throws InvalidParameterException {
    OutMsgEntity outMsgEntity = new OutMsgEntity();
    var result = loiLangService.searchWord(msgCmd.getArg(), false);
    String resultMsg = IntStream.range(0, result.size())
        .mapToObj(
            i -> String.format("#%d：%s", i + 1, result.get(i).getWord()))
        .collect(Collectors.joining("\n"));
    String content = String.format("搜到了%d条结果：\n%s\n使用【字典看字】指令查看详情。", result.size(), resultMsg);
    outMsgEntity.setContent(content);
    outMsgEntity.setMsgType(Constant.MSG_TYPE_TEXT);
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
    var result = loiLangService.getWord(msgCmd.getArg());
    String resultMsg = String.format("【%s】字共有%d个读音。",
        result.getWord(),
        result.getPinyinList().size()
    );
    outMsgEntity.setContent(resultMsg);
    outMsgEntity.setMsgType(Constant.MSG_TYPE_TEXT);
    return outMsgEntity;
  }
}
