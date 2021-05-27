package cn.yinjiahui.voa.portal.config;


import cn.yinjiahui.voa.portal.service.impl.UserServiceImpl;
import cn.yinjiahui.voa.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * mall-security模块相关配置
 * Created by macro on 2019/11/5.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class VoaSecurityConfig extends SecurityConfig {

    @Autowired
    private UserServiceImpl userService;

    @Override
    @Bean
    public UserDetailsService userDetailsService() {//获取登录用户信息
        return (username)->userService.loadUserByUsername(username);
    }
}
