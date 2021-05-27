package cn.yinjiahui.voa.portal.service;

import cn.yinjiahui.voa.db.model.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {


    String login(String username, String password);

    Integer getCurrentUserId();

    String getUsernameByPhone(String phone);

    String getIdByPhone(String phone);

    UserInfo getUsernameIdByPhone(String phone);

    void updateAvatar(MultipartFile file) throws IOException;

    UserInfo getUserInfo();

    void updateUserInfo(UserInfo userInfo);
}
