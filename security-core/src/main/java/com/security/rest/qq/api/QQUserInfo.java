package com.security.rest.qq.api;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * QQ用户信息封装
 */
public class QQUserInfo {

    private String openId;

    private int ret;
    private String msg;
    private String nickname;
    private String figureurl;
    private String figureurl_1;
    private String figureurl_2;
    private String figureurl_qq_1;
    private String figureurl_qq_2;
    private String gender;
    @JSONField(name = "is_yellow_vip")
    private String isYellowVip;
    private String vip;
    @JSONField(name = "yellow_vip_level")
    private String yellowVipLevel;
    private String level;
    @JSONField(name = "is_yellow_year_vip")
    private String isYellowYearVip;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFigureurl() {
        return figureurl;
    }

    public void setFigureurl(String figureurl) {
        this.figureurl = figureurl;
    }

    public String getFigureurl_1() {
        return figureurl_1;
    }

    public void setFigureurl_1(String figureurl_1) {
        this.figureurl_1 = figureurl_1;
    }

    public String getFigureurl_2() {
        return figureurl_2;
    }

    public void setFigureurl_2(String figureurl_2) {
        this.figureurl_2 = figureurl_2;
    }

    public String getFigureurl_qq_1() {
        return figureurl_qq_1;
    }

    public void setFigureurl_qq_1(String figureurl_qq_1) {
        this.figureurl_qq_1 = figureurl_qq_1;
    }

    public String getFigureurl_qq_2() {
        return figureurl_qq_2;
    }

    public void setFigureurl_qq_2(String figureurl_qq_2) {
        this.figureurl_qq_2 = figureurl_qq_2;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIsYellowVip() {
        return isYellowVip;
    }

    public void setIsYellowVip(String isYellowVip) {
        this.isYellowVip = isYellowVip;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getYellowVipLevel() {
        return yellowVipLevel;
    }

    public void setYellowVipLevel(String yellowVipLevel) {
        this.yellowVipLevel = yellowVipLevel;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIsYellowYearVip() {
        return isYellowYearVip;
    }

    public void setIsYellowYearVip(String isYellowYearVip) {
        this.isYellowYearVip = isYellowYearVip;
    }
}
