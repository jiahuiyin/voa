package cn.yinjiahui.voa.portal.service;

import cn.yinjiahui.voa.common.api.CommonResult;
import cn.yinjiahui.voa.db.model.UserInfo;

public interface RegisterService {

    void sendCode(String phone) throws Exception ;


    CommonResult register(UserInfo user, String authCode);
}
