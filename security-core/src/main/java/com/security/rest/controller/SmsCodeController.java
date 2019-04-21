package com.security.rest.controller;

import com.security.rest.common.SecurityProperties;
import com.security.rest.util.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/sms")
@Slf4j
public class SmsCodeController {

    @Autowired
    private SecurityProperties securityProperties;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    public final static String SESSION_SMS_KEY = "sms_key";

    /**
     * 获取验证码
     * @return
     */
    @GetMapping("/get_code")
    public String getSmsCode(HttpServletRequest request) throws Exception{

        String code = SmsUtil.getCode(securityProperties.getSms().getCodeLength());
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_SMS_KEY, code); //往session放入 验证码
        String mobile = ServletRequestUtils.getStringParameter(request, "mobile");
        log.info("mobile:" + mobile + ", code :" + code);
        SmsUtil.sendMsg(mobile, code);
        return code;
    }

}
