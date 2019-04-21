package com.security.rest.authentication.mobile;

import com.security.rest.common.SecurityConstant;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String SECURITY_MOBILE_KEY = "mobile";
    private String mobileParameter = SECURITY_MOBILE_KEY;
    private boolean postOnly = true;// 只允许post请求

    public SmsAuthenticationFilter() {
        /**
         * 对"/authentication/mobile"的登陆进行拦截
         */
        super(new AntPathRequestMatcher(SecurityConstant.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, "POST"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String mobile = obtainMobile(request);
        if (mobile == null) {
            mobile = "";
        }

        mobile = mobile.trim();

        SmsAuthenticationToken authRequest = new SmsAuthenticationToken(mobile);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(SECURITY_MOBILE_KEY);
    }

    protected void setDetails(HttpServletRequest request, SmsAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
    public void setUsernameParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "mobile parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter() {
        return mobileParameter;
    }

}
