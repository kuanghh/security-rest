package com.security.rest.config;

import com.security.rest.common.properties.PropertiesConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PropertiesConfig.class) // 绑定 属性Configure
public class BrowserConfig {
}
