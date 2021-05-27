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
package cn.yinjiahui.voa.portal.controller;

import cn.yinjiahui.voa.common.api.CommonResult;
import cn.yinjiahui.voa.portal.push.DefaultMessagePusher;
import cn.yinjiahui.voa.portal.service.MessageService;
import com.farsunset.cim.sdk.server.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/message")
public class MessageController {

	@Resource
	private DefaultMessagePusher defaultMessagePusher;

	@Autowired
	MessageService messageService;

	@PostMapping(value = "")
	public CommonResult send(Message message){
		defaultMessagePusher.push(message);
		return CommonResult.success(message.getId());
	}

	@GetMapping("")
	public CommonResult getMessage(@RequestParam String receiverId){
		List message = messageService.getMessage(Integer.valueOf(receiverId));
		return CommonResult.success(message);
	}

}
