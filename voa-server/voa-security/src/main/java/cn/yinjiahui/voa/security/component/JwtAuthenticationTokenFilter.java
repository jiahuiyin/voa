package cn.yinjiahui.voa.security.component;


import cn.yinjiahui.voa.security.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

/**
 * JWT登录授权过滤器
 * Created by macro on 2018/4/26.
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String authToken = request.getHeader(tokenHeader);
        if (authToken != null) {
            String username = jwtTokenUtil.getUserNameFromToken(authToken);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {


                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null,new LinkedList<>());

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }
        chain.doFilter(request, response);
    }
}
