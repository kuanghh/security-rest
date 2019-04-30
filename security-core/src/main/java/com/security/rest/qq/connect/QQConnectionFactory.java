package com.security.rest.qq.connect;


import com.security.rest.qq.api.QQ;
import com.security.rest.qq.api.QQProvider;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * 创建Connection的工厂
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    public QQConnectionFactory(String providerId,String appId, String appSecret) {
        super(providerId, new QQProvider(appId, appSecret), new QQApiAdapter());
    }
}
