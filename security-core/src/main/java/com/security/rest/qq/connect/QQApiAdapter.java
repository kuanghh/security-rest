package com.security.rest.qq.connect;

import com.security.rest.qq.api.QQ;
import com.security.rest.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 用户信息转换器
 */
public class QQApiAdapter implements ApiAdapter<QQ> {

    @Override
    public boolean test(QQ api) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo user = api.getQQUserInfo();
        values.setDisplayName(user.getNickname());
        values.setImageUrl(user.getFigureurl_qq_1());//头像
        values.setProviderUserId(user.getOpenId());
        values.setProfileUrl(null);
    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {
        //do nothing
    }
}
