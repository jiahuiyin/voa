package cn.yinjiahui.voa.portal.service;

import com.farsunset.cim.sdk.server.model.Message;

import java.util.List;

public interface MessageService {



    void addMessage(Message message,Integer visited);

    List getMessage(Integer id);
}
