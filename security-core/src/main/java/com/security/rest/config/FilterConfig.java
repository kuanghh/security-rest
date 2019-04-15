package com.security.rest.config;

import com.google.code.kaptcha.Producer;
import com.security.rest.cache.CustomerCache;
import com.security.rest.common.SecurityProperties;
import com.security.rest.filter.CheckCodeFilter;
import com.security.rest.filter.CheckCodeFilterImpl;
import com.security.rest.filter.VerifyCodeFilter;
import com.security.rest.filter.VerifyCodeFilterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class FilterConfig implements WebMvcConfigurer {


    @Autowired
    private Producer producer;

    @Autowired
    private CustomerCache customerCache;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 配置获取验证码的过滤器
     */
    @Bean
    @ConditionalOnExpression("${khh.security.code.useDefaultVerify:true}")
    public FilterRegistrationBean verifyCodeFilterRegistration(VerifyCodeFilter verifyCodeFilter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(verifyCodeFilter);
        registrationBean.addUrlPatterns(securityProperties.getCode().getGetCodeUrl());
        registrationBean.setName("verifyCodeFilter");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registrationBean;
    }

    /**
     * 配置校验验证码的过滤器
     */
    @Bean
    @ConditionalOnExpression("${khh.security.code.useDefaultVerify:true}")
    public FilterRegistrationBean checkCodeFilterRegistration(CheckCodeFilter checkCodeFilter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(checkCodeFilter);
        registrationBean.addUrlPatterns(securityProperties.getCode().getCheckCodeUrl());
        registrationBean.setName("checkCodeFilter");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 2);
        return registrationBean;
    }


    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @ConditionalOnMissingBean(name = "verifyCodeFilter")
    public VerifyCodeFilterImpl verifyCodeFilter(){
        VerifyCodeFilterImpl verifyCodeFilterImpl = new VerifyCodeFilterImpl();
        verifyCodeFilterImpl.setProducer(producer);
        verifyCodeFilterImpl.setCustomerCache(customerCache);
        return verifyCodeFilterImpl;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @ConditionalOnMissingBean(name = "checkCodeFilter")
    public CheckCodeFilterImpl checkCodeFilter(){
        CheckCodeFilterImpl checkCodeFilterImpl = new CheckCodeFilterImpl();
        checkCodeFilterImpl.setCustomerCache(customerCache);
        return checkCodeFilterImpl;
    }

}
