package com.security.rest.qq.api;


import com.security.rest.qq.connect.QQOAuth2Template;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * 服务提供商
 */
public class QQProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appKey;

    //导向用户去认证服务器地址
    private final static String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    //申请令牌地址
    private final static String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    public QQProvider(String appId, String appSecret) {
        super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appKey = appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appKey);
    }
}
