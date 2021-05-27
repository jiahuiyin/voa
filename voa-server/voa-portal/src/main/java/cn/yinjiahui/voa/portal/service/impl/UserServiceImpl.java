package cn.yinjiahui.voa.portal.service.impl;

import cn.yinjiahui.voa.db.mapper.UserMapper;
import cn.yinjiahui.voa.db.model.UserInfo;
import cn.yinjiahui.voa.portal.service.UserService;
import cn.yinjiahui.voa.security.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Value("${voa.avatarpath}")
    private String avatarPath;
    @Autowired
    UserMapper userMapper;

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserInfo user = userMapper.getUserAuthByPhone(s);
        if(user==null) throw new UsernameNotFoundException("");
        return new User(user.getPhone(),user.getPassword(),new LinkedList<>());
    }


    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String userId=userMapper.getUserIdByPhone(userDetails.getUsername());
            token = jwtTokenUtil.generateToken(userId);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public Integer getCurrentUserId() {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        return Integer.valueOf(auth.getName());
    }

    @Override
    public String getUsernameByPhone(String phone) {
        return userMapper.getUsernameByPhone(phone);
    }

    @Override
    public String  getIdByPhone(String phone) {
        return userMapper.getUserIdByPhone(phone);
    }

    @Override
    public UserInfo getUsernameIdByPhone(String phone) {

        return userMapper.getUserInfoByPhone(phone);
    }

    @Override
    public void updateAvatar(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String trueName= UUID.randomUUID()+suffixName;
        File dest = new File(avatarPath + trueName);
        file.transferTo(dest);

        userMapper.updateAvatar(trueName,getCurrentUserId());
    }

    @Override
    public UserInfo getUserInfo() {
        return userMapper.getUserInfo(getCurrentUserId());
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        userInfo.setId(getCurrentUserId());
        userMapper.updateUserInfo(userInfo);

    }
}
