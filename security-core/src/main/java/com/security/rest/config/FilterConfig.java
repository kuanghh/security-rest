package com.security.rest.config;

import com.security.rest.common.SecurityConstant;
import com.security.rest.common.SecurityProperties;
import com.security.rest.filter.CheckCodeFilter;
import com.security.rest.filter.VerifyCodeFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;


@Configuration
@Slf4j
public class FilterConfig implements WebMvcConfigurer {


    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private Map<String, VerifyCodeFilter> verifyFilterMap;

    @Autowired
    private Map<String, CheckCodeFilter> checkCodeFilterMap;

    /**
     * 配置获取验证码的过滤器
     */
    @Bean
    @ConditionalOnExpression("${khh.security.code.useDefaultVerify:true}")
    public FilterRegistrationBean verifyCodeFilterRegistration() {
        FilterRegistrationBean<VerifyCodeFilter> registrationBean = new FilterRegistrationBean<>();
        if(!verifyFilterMap.containsKey(SecurityConstant.FilterName.IMG_VERFIY_CODE_FILTER)){
            return null;
        }
        VerifyCodeFilter verifyCodeFilter = verifyFilterMap.get(SecurityConstant.FilterName.IMG_VERFIY_CODE_FILTER);
        registrationBean.setFilter(verifyCodeFilter);
        registrationBean.addUrlPatterns(securityProperties.getCode().getGetCodeUrl());
        registrationBean.setName("verifyCodeFilter");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        log.info("注册获取图片校验码过滤器success");
        return registrationBean;
    }

    /**
     * 配置校验验证码的过滤器
     */
    @Bean
    @ConditionalOnExpression("${khh.security.code.useDefaultVerify:true}")
    public FilterRegistrationBean checkCodeFilterRegistration() {
        FilterRegistrationBean<CheckCodeFilter> registrationBean = new FilterRegistrationBean<>();
        if(!checkCodeFilterMap.containsKey(SecurityConstant.FilterName.IMG_CHECK_CODE_FILTER)){
            return null;
        }
        CheckCodeFilter checkCodeFilter = checkCodeFilterMap.get(SecurityConstant.FilterName.IMG_CHECK_CODE_FILTER);
        registrationBean.setFilter(checkCodeFilter);
        registrationBean.addUrlPatterns(securityProperties.getCode().getCheckCodeUrl());
        registrationBean.setName("checkCodeFilter");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 2);
        log.info("注册校验图片校验码过滤器success");
        return registrationBean;
    }

}
