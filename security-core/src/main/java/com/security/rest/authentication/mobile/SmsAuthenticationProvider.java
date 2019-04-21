package com.security.rest.authentication.mobile;

import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Data
@Component
public class SmsAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //强转一下,这是来源于SmsAuthenticationFilter的attemptAuthentication方法结果
        //未认证的token
        SmsAuthenticationToken token = (SmsAuthenticationToken)authentication;

        UserDetails user = userDetailsService.loadUserByUsername((String) token.getPrincipal());
        if(user == null){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        //放回用户以及权限
        //已认证的token
        SmsAuthenticationToken userToken = new SmsAuthenticationToken(user, user.getAuthorities());
        userToken.setDetails(authentication.getDetails());
        return userToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
