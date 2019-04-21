package com.security.rest.util;

import org.apache.commons.lang.RandomStringUtils;

/**
 * 短信工具类
 */
public class SmsUtil {


    /**
     * 获取随机短信验证码
     * @param length 长度
     * @return
     */
    public static String getCode(Integer length){
        return RandomStringUtils.randomNumeric(length);
    }

    public static void sendMsg(String mobile, String msg){
        System.out.println("send Msg : " + msg);
    }
}
