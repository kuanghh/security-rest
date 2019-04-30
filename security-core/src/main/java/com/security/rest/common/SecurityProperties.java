package com.security.rest.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static com.security.rest.common.SecurityConstant.*;

@Data
@ConfigurationProperties(prefix = "khh.security") //匹配并读取配置文件中前缀为khh.security的属性
@Component
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();
    private VerifyCodeProperties code = new VerifyCodeProperties();
    private SmsCodeProperties sms = new SmsCodeProperties();
    private QQProperties qq = new QQProperties();

    @Data
    public static class BrowserProperties{
        private String loginPage = "/login.html";
        private LoginType loginType = LoginType.JSON;
        private Integer tokenValiditySeconds = 3600;
    }

    @Data
    public static class VerifyCodeProperties{
        private Boolean useDefaultVerify = true; //是否启用默认验证码校验器 //默认为true
        private String getCodeUrl = GET_VALIDATE_CODE_URL; //获取验证码路径
        private String checkCodeUrl = DEFAULT_LOGIN_PROCESSING_URL_FORM; //校验验证码 url的路径
    }

    @Data
    public static class SmsCodeProperties{
        private Integer codeLength = 4; // 短信验证码长度
        private String getSmsCodeUrl = GET_SMS_CODE_URL;
        private String checkCodeUrl = DEFAULT_LOGIN_PROCESSING_URL_MOBILE;
    }

    @Data
    public static class QQProperties{
        private String providerId;
        private String appId;
        private String appSecret;
    }
}
