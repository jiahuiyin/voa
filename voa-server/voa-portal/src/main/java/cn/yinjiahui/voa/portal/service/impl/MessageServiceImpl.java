package cn.yinjiahui.voa.portal.service.impl;

import cn.yinjiahui.voa.db.mapper.MessageMapper;
import cn.yinjiahui.voa.db.model.MessageInfo;
import cn.yinjiahui.voa.portal.service.MessageService;
import cn.yinjiahui.voa.portal.service.UserService;
import cn.yinjiahui.voa.portal.util.MessageUtil;
import com.farsunset.cim.sdk.server.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    UserService userService;
    @Override
    public void addMessage(Message message,Integer visited) {
        MessageInfo messageInfo = MessageUtil.getMessageInfo(message);
        messageMapper.addMessage(messageInfo,visited);
    }


    @Override
    public List getMessage(Integer id) {
        Integer currentUserId=userService.getCurrentUserId();
        List<MessageInfo> send = messageMapper.getMessage(currentUserId, id);
        return send;
    }
}
