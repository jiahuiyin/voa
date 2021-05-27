package cn.yinjiahui.voa.portal.controller;

import cn.yinjiahui.voa.common.api.CommonResult;
import cn.yinjiahui.voa.db.model.UserInfo;
import cn.yinjiahui.voa.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PutMapping("/avatar")
    public CommonResult updateAvatar(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            return CommonResult.failed();
        }
        try {
            userService.updateAvatar(file);
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }

    @GetMapping("/phone/{phone}")
    public CommonResult getUserInfoByPhone(@PathVariable("phone") String phone){
        UserInfo userInfo = userService.getUsernameIdByPhone(phone);
        if(userInfo==null){
            return CommonResult.failed("未找到");
        }else{
            return CommonResult.success(userInfo);
        }
    }

    @GetMapping("")
    public CommonResult getUserInfo(){
        return CommonResult.success(userService.getUserInfo());
    }

    @PutMapping("")
    public CommonResult updateUserInfo(UserInfo userInfo){
        userService.updateUserInfo(userInfo);
        return getUserInfo();
    }

}
