package cn.yinjiahui.voa.portal.service.impl;

import cn.yinjiahui.voa.common.api.CommonResult;
import cn.yinjiahui.voa.common.service.RedisService;
import cn.yinjiahui.voa.db.mapper.UserMapper;
import cn.yinjiahui.voa.db.model.UserInfo;
import cn.yinjiahui.voa.portal.service.RegisterService;
import cn.yinjiahui.voa.portal.service.UserService;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
@Service
public class RegisterServiceImpl implements RegisterService {


    @Value("${voa.code.templateCode}")
    private String templateCode;
    @Value("${voa.code.signName}")
    private String signName;

    @Autowired
    RedisService redisService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;
    @Autowired
    Client client;
    @Override
    public void sendCode(String phone)  throws Exception{
        String code = String.format("%04d",new Random().nextInt(9999));
        redisService.set(phone,code,300);
        sendSmsResponse(phone,code);
    }




    private void sendSmsResponse(String phoneNumber, String code)  throws Exception{

        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phoneNumber)
                .setSignName(signName)
                .setTemplateCode(templateCode)
                .setTemplateParam("{\"code\":\""+code+"\"}");
        client.sendSms(sendSmsRequest);
    }


    @Override
    public CommonResult register(UserInfo user, String authCode) {

        try {
            String code = (String) redisService.get(user.getPhone());
            if (!authCode.equals(code)){
                return CommonResult.failed("验证码错误");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userMapper.addUser(user);
            String token = userService.login(user.getPhone(), user.getPassword());

            Map<String, Object> tokenMap = new HashMap<>(3);
            String name = user.getUsername();
            Integer id=user.getId();
            tokenMap.put("name",name);
            tokenMap.put("id",id);
            tokenMap.put("token", token);
            return CommonResult.success(tokenMap);
        }catch (DuplicateKeyException e){
            return CommonResult.failed("手机已存在");
        }

    }
}
