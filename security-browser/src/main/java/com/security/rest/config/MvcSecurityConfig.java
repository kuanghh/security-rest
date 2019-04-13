package com.security.rest.config;

import com.security.rest.service.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Slf4j
@Configuration
public class MvcSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private MyPasswordEncoderChooser myPasswordEncoderChooser;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("这里进行配置 自定义用户认证的方法，并且配置 密码加密器");
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(myPasswordEncoderChooser.getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("开启 springSecurity ");
//        http.httpBasic()
        http.formLogin()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }


    @Bean
    public UserDetailsService userDetailsService(){
        return new MyUserDetailsService();
    }
}
