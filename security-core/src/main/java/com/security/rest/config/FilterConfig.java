package com.security.rest.config;

import com.security.rest.filter.CheckCodeFilter;
import com.security.rest.filter.VerifycodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.security.rest.common.SecurityConstant.DEFAULT_LOGIN_PROCESSING_URL_FORM;
import static com.security.rest.common.SecurityConstant.GET_VALIDATE_CODE_URL;

@Configuration
public class FilterConfig implements WebMvcConfigurer {

    @Autowired
    private VerifycodeFilter verifycodeFilter;

    @Autowired
    private CheckCodeFilter checkCodeFilter;

    /**
     * 配置获取验证码的过滤器
     */
    @Bean
    public FilterRegistrationBean verifycodeFilterRegistration() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(verifycodeFilter);
        registrationBean.addUrlPatterns(GET_VALIDATE_CODE_URL);
        registrationBean.setName("verifycodeFilter");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registrationBean;
    }

    /**
     * 配置校验验证码的过滤器
     */
    @Bean
    public FilterRegistrationBean checkCodeFilterRegistration() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(checkCodeFilter);
        registrationBean.addUrlPatterns(DEFAULT_LOGIN_PROCESSING_URL_FORM);
        registrationBean.setName("checkCodeFilter");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 2);
        return registrationBean;
    }


}
