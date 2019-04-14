package com.security.rest.controller;

import com.security.rest.common.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * 实现可自定义配置,用户跳转的登陆界面
 * 原理：
 *  当用户访问以.html结尾的资源时,会经过springSecurity,并判断是否已经认证了
 *  如果没有,则会跳转到"/authentication/require"请求,
 *  通过该controller来读取自定义的登陆界面配置
 */
@Slf4j
@RestController
@RequestMapping("/authentication")
public class RestSecurityController {

    @Autowired
    private SecurityProperties securityProperties;

    //经过springSecurity的请求都会被存在这里
    //它是怎么做到的,通过request里面session里面取到的，可看源码
    private RequestCache requestCache = new HttpSessionRequestCache();

    //重定向工具
    private RedirectStrategy strategy = new DefaultRedirectStrategy();

    /**
     * 当需要认证时会跳转到这里
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public String authentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("进入自定认证流程");
        HttpSession session = request.getSession();
        log.info("session : " + ReflectionToStringBuilder.toString(session, ToStringStyle.SHORT_PREFIX_STYLE));
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if(savedRequest != null){
            log.info("saveRequest : " + ReflectionToStringBuilder.toString(savedRequest, ToStringStyle.SHORT_PREFIX_STYLE));
            String redirectUrl = savedRequest.getRedirectUrl();
            //如果跳转地址以.html结尾(用户的请求以html结尾)
            if(StringUtils.endsWithIgnoreCase(redirectUrl, ".html")){
                log.info("redirectUrl : " + redirectUrl);
                strategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }
        }

        return "请先登陆";
    }

}
