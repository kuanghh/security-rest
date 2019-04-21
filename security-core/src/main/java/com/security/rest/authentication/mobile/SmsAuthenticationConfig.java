package com.security.rest.authentication.mobile;

import com.security.rest.authentication.authentication.DefaultAuthFailureHandler;
import com.security.rest.authentication.authentication.DefaultAuthSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SmsAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private DefaultAuthSuccessHandler successHandler;

    @Autowired
    private DefaultAuthFailureHandler failureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //配置过滤器
        SmsAuthenticationFilter smsAuthenticationFilter = new SmsAuthenticationFilter();
        smsAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        smsAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        smsAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);

        //配置provider
        SmsAuthenticationProvider provider = new SmsAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);

        http.authenticationProvider(provider)
                .addFilterAfter(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
