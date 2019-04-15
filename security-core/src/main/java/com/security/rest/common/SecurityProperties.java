package com.security.rest.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static com.security.rest.common.SecurityConstant.DEFAULT_LOGIN_PROCESSING_URL_FORM;
import static com.security.rest.common.SecurityConstant.GET_VALIDATE_CODE_URL;

@Data
@ConfigurationProperties(prefix = "khh.security") //匹配并读取配置文件中前缀为khh.security的属性
@Component
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();
    private VerifyCodeProperties code = new VerifyCodeProperties();

    @Data
    public static class BrowserProperties{
        private String loginPage = "/login.html";
        private LoginType loginType = LoginType.JSON;
    }

    @Data
    public static class VerifyCodeProperties{
        private Boolean useDefaultVerify = true; //是否启用默认验证码校验器 //默认为true
        private String getCodeUrl = GET_VALIDATE_CODE_URL; //获取验证码路径
        private String checkCodeUrl = DEFAULT_LOGIN_PROCESSING_URL_FORM; //校验验证码 url的路径
    }
}
