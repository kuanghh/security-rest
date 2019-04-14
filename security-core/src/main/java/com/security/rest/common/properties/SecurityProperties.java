package com.security.rest.common.properties;

import com.security.rest.common.LoginType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "khh.security") //匹配并读取配置文件中前缀为khh.security的属性
@Component
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

    @Data
    public static class BrowserProperties{
        private String loginPage = "/login.html";
        private LoginType loginType = LoginType.JSON;
    }
}
