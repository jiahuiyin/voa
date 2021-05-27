package cn.yinjiahui.voa.portal.controller;

import cn.yinjiahui.voa.common.api.CommonResult;
import cn.yinjiahui.voa.db.model.UserInfo;
import cn.yinjiahui.voa.portal.service.RegisterService;
import cn.yinjiahui.voa.portal.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 会员登录注册管理Controller
 * Created by macro on 2018/8/3.
 */
@Slf4j
@RestController
@RequestMapping("/sso")
public class SsoController {


    @Autowired
    private UserService userService;

    @Autowired
    private RegisterService registerService;

    @PostMapping("/login")
    public CommonResult login(@RequestParam String username,
                              @RequestParam String password) {
        String token = userService.login(username, password);
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, Object> tokenMap = new HashMap<>(3);
        String name = userService.getUsernameByPhone(username);
        String id=userService.getIdByPhone(username);
        tokenMap.put("name",name);
        tokenMap.put("id",id);
        tokenMap.put("token", token);
        return CommonResult.success(tokenMap);
    }

    @PostMapping("/register")
    public CommonResult register(UserInfo userInfo, String code){
        return registerService.register(userInfo,code);
    }

    @PostMapping("/code")
    public CommonResult getCode(String phone){

        try {
            registerService.sendCode(phone);
        } catch (Exception e) {
            log.error(e.toString());
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }

}
