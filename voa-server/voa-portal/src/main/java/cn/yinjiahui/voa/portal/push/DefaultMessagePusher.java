/*
 * Copyright 2013-2019 Xia Jun(3979434@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ***************************************************************************************
 *                                                                                     *
 *                        Website : http://www.farsunset.com                           *
 *                                                                                     *
 ***************************************************************************************
 */
package cn.yinjiahui.voa.portal.push;

import cn.yinjiahui.voa.portal.service.*;
import com.farsunset.cim.sdk.server.model.CIMSession;
import com.farsunset.cim.sdk.server.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/*
 * 消息发送实现类
 * 
 */
@Component
public class DefaultMessagePusher implements CIMMessagePusher {

	@Value("${server.host}")
	private String host;
	
	@Resource
	private CIMSessionService cimSessionService;

	@Autowired
	MessageService messageService;
	

	@Resource
	private ApnsService apnsService;
	
	
	/*
	 * 向用户发送消息
	 * 
	 * @param message
	 */
	@Override
	public void push(Message message) {
		CIMSession session = cimSessionService.get(message.getReceiver());

		if(session == null) {
			messageService.addMessage(message,0);
			return;
		}


		if (session.isConnected() && Objects.equals(host, session.getHost())) {
			session.write(message);
			messageService.addMessage(message,1);
		}

		
	}

}
