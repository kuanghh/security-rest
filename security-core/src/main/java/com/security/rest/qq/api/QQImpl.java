package com.security.rest.qq.api;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * 获取QQ个人信息
 */
@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ{

    private String appKey;

    private String openId;

    private final static String URL_GET_USER_OEPNID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    private final static String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    public QQImpl(String token, String appKey){
        /*父类默认将token放入httpHeader里面*/
        super(token, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appKey = appKey;

        String url = String.format(URL_GET_USER_OEPNID, token);
        String result = this.getRestTemplate().getForObject(url, String.class);
        openId = StringUtils.substringBetween(result,"openid\":\"","\"}");//获取用户openId
        log.info(String.format("token为%s用户的openId为%s", token, openId));
    }

    @Override
    public QQUserInfo getQQUserInfo(){
        //这里不放token是因为父类已经帮我们放了
        String url = String.format(URL_GET_USER_INFO, appKey, openId);
        QQUserInfo user = this.getRestTemplate().getForObject(url, QQUserInfo.class);
        if(user == null){
            log.error(String.format("openid为%s用户获取信息失败", openId));
            return null;
        }
        log.info("用户信息:" + ReflectionToStringBuilder.toString(user, ToStringStyle.SIMPLE_STYLE));
        user.setOpenId(openId);
        return user;
    }
}
