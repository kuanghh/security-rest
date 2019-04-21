package com.security.rest.authentication.authentication;

import com.alibaba.fastjson.JSONObject;
import com.security.rest.common.LoginType;
import com.security.rest.common.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理用户认证失败handler
 */
@Component("browserDefaultAuthFailureHandler")
@Slf4j
public class DefaultAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    /**
     * 这里除了实现 AuthenticationFailureHandler,还可以继承
     * Spring自带的SimpleUrlAuthenticationFailureHandler，
     * 作用是返回错误页面
     */

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response
            , AuthenticationException exception) throws IOException, ServletException {

        //exception 是登陆失败的原因
        log.info("登陆失败");
        if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSONObject.toJSONString(exception.getMessage()));
        }else{
            super.onAuthenticationFailure(request, response, exception);
        }

    }
}
