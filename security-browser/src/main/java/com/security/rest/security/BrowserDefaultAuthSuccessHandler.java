package com.security.rest.security;

import com.alibaba.fastjson.JSONObject;
import com.security.rest.common.LoginType;
import com.security.rest.common.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理用户认证成功handler
 */
@Component("browserDefaultAuthSuccessHandler")
@Slf4j
public class BrowserDefaultAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    /**
     * 这里除了实现 AuthenticationSuccessHandler,还可以继承
     * Spring自带的SavedRequestAwareAuthenticationSuccessHandler，
     * 作用是重定向原来访问的页面
     */

    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response
            , Authentication authentication) throws IOException, ServletException {
        /**
         * authentication存放的是 用户认证成功后的信息
         */
        log.info("登陆成功");
        if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSONObject.toJSONString(authentication));
        }else{
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
