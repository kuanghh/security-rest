package com.security.rest.qq.connect;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * QQ自定义的OAuth2Template
 */
@Slf4j
public class QQOAuth2Template extends OAuth2Template {

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);//需要携带必要参数，可以看源码
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        //增加可支持解析text/html的返回数据
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String responseStr = this.getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        log.info("获取用户令牌的response:" + responseStr);
        //由于qq获取access token的返回数据类似access_token=FE04***CCE2&expires_in=7776000&refresh_token=88E4*****BE14
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");
        if(items == null){
            throw new RuntimeException("获取access_token失败");
        }
        Map<String, Object> resMap = Arrays.stream(items).collect(Collectors.toMap(s -> StringUtils.substringBefore(s, "="),
                s -> StringUtils.substringAfter(s, "=")));
        QQTokenResponse response = JSONObject.parseObject(JSONObject.toJSONString(resMap), QQTokenResponse.class);
        return new AccessGrant(response.getAccess_token(), null,response.getRefresh_token(),response.getExpires_in());
    }

    @Data
    private static class QQTokenResponse{
        private String access_token;
        private String refresh_token;
        private Long expires_in;
    }
}
