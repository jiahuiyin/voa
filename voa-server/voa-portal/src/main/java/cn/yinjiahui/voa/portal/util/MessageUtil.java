package cn.yinjiahui.voa.portal.util;

import cn.yinjiahui.voa.db.model.MessageInfo;
import com.farsunset.cim.sdk.server.model.Message;

import java.util.Date;

public class MessageUtil {

    public static MessageInfo getMessageInfo(Message message){
        MessageInfo messageInfo=new MessageInfo();
        messageInfo.setAction(message.getAction());
        messageInfo.setContent(message.getContent());
        messageInfo.setReceiver(message.getReceiver());
        messageInfo.setSender(message.getSender());
        messageInfo.setCreatTime(new Date(message.getTimestamp()));
        return messageInfo;
    }
}
