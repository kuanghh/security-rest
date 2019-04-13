package com.security.rest.config;

import com.security.rest.common.properties.PropertiesConfig;
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
    private PropertiesConfig propertiesConfig;

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
        log.info("开启 springSecurity");
//        http.httpBasic()
        http.formLogin()
                .loginPage("/authentication/require")//指定登陆页面url
                .loginProcessingUrl("/authentication/form") //此设置登录页面的登陆认证请求url路径
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require",propertiesConfig.getBrowser().getLoginPage())
                    .permitAll() //登陆页面的请求允许访问
                .anyRequest() //其他请求
                .authenticated() //都要经过认证
                .and()
                .csrf().disable(); //暂不使用 springSecurity的csrf功能
    }


    @Bean
    public UserDetailsService userDetailsService(){
        return new MyUserDetailsService();
    }
}
