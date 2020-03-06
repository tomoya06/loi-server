package com.tomoya06.loiserver.wechat.service;

import static com.tomoya06.loiserver.wechat.model.DO.LoiLangMsgCmd.ADD;
import static com.tomoya06.loiserver.wechat.model.DO.LoiLangMsgCmd.ADD_PINYIN;
import static com.tomoya06.loiserver.wechat.model.DO.LoiLangMsgCmd.DELETE;
import static com.tomoya06.loiserver.wechat.model.DO.LoiLangMsgCmd.DELETE_PINYIN;
import static com.tomoya06.loiserver.wechat.model.DO.LoiLangMsgCmd.GENERAL;
import static com.tomoya06.loiserver.wechat.model.DO.LoiLangMsgCmd.SEARCH;
import static com.tomoya06.loiserver.wechat.model.DO.LoiLangMsgCmd.SHOW;

import com.tomoya06.loiserver.loilang.model.DO.LoiLangDocument.LangType;
import com.tomoya06.loiserver.loilang.service.LoiLangService;
import com.tomoya06.loiserver.wechat.common.Constant;
import com.tomoya06.loiserver.wechat.model.DO.LoiLangMsgCmd;
import com.tomoya06.loiserver.wechat.model.DTO.OutMsgEntity;
import java.security.InvalidParameterException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.management.InstanceAlreadyExistsException;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class WechatMsgLoilangService {

  @Autowired
  private LoiLangService loiLangService;

  @Value("${loilang.status}")
  private String loiLangStatus;

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
        case ADD:
          return handleAddWord(msgCmd);
        case ADD_PINYIN:
          return handleAddPinyin(msgCmd);
        case DELETE_PINYIN:
          return handleDeletePinyin(msgCmd);
        case DELETE:
          return handleDeleteWord(msgCmd);
        default:
          return null;
      }
    } catch (InvalidParameterException e) {
      OutMsgEntity outMsgEntity = new OutMsgEntity();
      outMsgEntity.setContent("换个参数试试");
      outMsgEntity.setMsgType(Constant.MSG_TYPE_TEXT);
      return outMsgEntity;
    } catch (InstanceAlreadyExistsException e) {
      OutMsgEntity outMsgEntity = new OutMsgEntity();
      outMsgEntity.setContent("已经加过了，不客气");
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

    String content = "黎话字典bot"
        + "\n当前状态：" + loiLangStatus
        + "\n目前所有可用指令："
        + "\n“字典多厚”：查看字典概况"
        + "\n“字典查字 我”：搜索以“我”开头的词条"
        + "\n“字典看字 我”：查看“我”词条"
        + "\n[TBD]“字典加字 他 pinyin”：添加“他”字入典，配置其读音为“pinyin”"
        + "\n[TBD]“字典加音 他 pin”：给“他”字添加新读音，即多音字"
        + "\n[TBD]“字典删音 他 pin”：删除“他”字的“pin”读音"
        + "\n[BETA]“字典删字 他”：删除字典中的“他”字"
        + "\n[NEY]“字典缺字 哈”：上报字典尚未录入的词条"
        + "\n"
        + "\n* [TBD]: Cmd may be deleted after beta test."
        + "\n* [BETA]: Cmd only exists during beta test. "
        + "\n* [NEY]: Cmd is not existing yet.  ";
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
    String content = String.format("目前黎话字典总共收录了%d个词条。"
        + "\n最近加入的词条有："
        + "\n%s", result.getTotal(), latestWord);
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
    var result = loiLangService.search(msgCmd.getArg());
    String resultMsg = IntStream.range(0, result.size())
        .mapToObj(
            i -> String.format("#%d：%s，共有%d个读音；", i + 1, result.get(i).getWord(), result.get(i).getPinyins().size()))
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
    String resultMsg = String.format("【%s】字共有%d个读音：%s。",
        result.getWord(),
        result.getPinyins().size(),
        String.join("，", result.getPinyins())
    );
    outMsgEntity.setContent(resultMsg);
    outMsgEntity.setMsgType(Constant.MSG_TYPE_TEXT);
    return outMsgEntity;
  }

  /**
   * 加字
   *
   * @param msgCmd
   * @return
   */
  private OutMsgEntity handleAddWord(LoiLangMsgCmd msgCmd)
      throws InstanceAlreadyExistsException, InvalidParameterException {
    OutMsgEntity outMsgEntity = new OutMsgEntity();
    var result = loiLangService.create(msgCmd.getArg(), msgCmd.getArg0(), LangType.NORMAL);
    String content = String.format("【%s】加好了。", result.getWord());
    outMsgEntity.setContent(content);
    outMsgEntity.setMsgType(Constant.MSG_TYPE_TEXT);
    return outMsgEntity;
  }

  /**
   * 加读音
   *
   * @param msgCmd
   * @return
   */
  private OutMsgEntity handleAddPinyin(LoiLangMsgCmd msgCmd) throws InstanceAlreadyExistsException {
    OutMsgEntity outMsgEntity = new OutMsgEntity();
    loiLangService.addMultiPron(msgCmd.getArg(), msgCmd.getArg0());
    String content = String.format("【%s】的读音\"%s\"加好了。", msgCmd.getArg(), msgCmd.getArg0());
    outMsgEntity.setContent(content);
    outMsgEntity.setMsgType(Constant.MSG_TYPE_TEXT);
    return outMsgEntity;
  }

  /**
   * 删读音
   *
   * @param msgCmd
   * @return
   */
  private OutMsgEntity handleDeletePinyin(LoiLangMsgCmd msgCmd) {
    OutMsgEntity outMsgEntity = new OutMsgEntity();
    String deletedPinyin = loiLangService.deletePinyin(msgCmd.getArg(), Integer.parseInt(msgCmd.getArg0()));
    String content = String.format("【%s】的读音\"%s\"删掉了。", msgCmd.getArg(), deletedPinyin);
    outMsgEntity.setContent(content);
    outMsgEntity.setMsgType(Constant.MSG_TYPE_TEXT);
    return outMsgEntity;
  }

  /**
   * 删字
   *
   * @param msgCmd
   * @return
   */
  private OutMsgEntity handleDeleteWord(LoiLangMsgCmd msgCmd) {
    OutMsgEntity outMsgEntity = new OutMsgEntity();
    loiLangService.deleteWord(msgCmd.getArg());
    String content = String.format("【%s】字删掉了。", msgCmd.getArg());
    outMsgEntity.setContent(content);
    outMsgEntity.setMsgType(Constant.MSG_TYPE_TEXT);
    return outMsgEntity;
  }
}
