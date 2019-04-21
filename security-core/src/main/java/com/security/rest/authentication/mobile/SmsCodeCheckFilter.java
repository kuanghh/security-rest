package com.security.rest.authentication.mobile;

import com.alibaba.fastjson.JSONObject;
import com.security.rest.common.SecurityProperties;
import com.security.rest.controller.SmsCodeController;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Sets;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

@Data
public class SmsCodeCheckFilter extends OncePerRequestFilter implements InitializingBean {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    private Set<String> urlSet = Sets.newHashSet();

    private SecurityProperties securityProperties;

    @Override
    public void afterPropertiesSet() throws ServletException {
        String checkCodeUrl = securityProperties.getSms().getCheckCodeUrl();
        if(!StringUtils.isEmpty(checkCodeUrl)){
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(checkCodeUrl, ",");
            urlSet.addAll(Arrays.asList(urls));
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        for (String url : urlSet) {
            if (pathMatcher.match(url, request.getRequestURI())) {
                //获取request的验证码
                String smsCode = ServletRequestUtils.getStringParameter(request, "smsCode");
                if(smsCode == null){
                    printResult(response, "请输入验证码");
                    return;
                }
                //获取session的验证码
                String code = (String) sessionStrategy.getAttribute(new ServletWebRequest(request), SmsCodeController.SESSION_SMS_KEY);
                if(!smsCode.equals(code)){
                    printResult(response, "验证码不正确");
                }
            }
        }
        chain.doFilter(request, response);
    }

    private void printResult(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(message));
    }
}
