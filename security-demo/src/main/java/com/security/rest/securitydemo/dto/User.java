package com.security.rest.securitydemo.dto;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;
import java.util.Date;


public class User implements Serializable {

    /**声明两个视图**/
    public interface UserSimpleView{}
    public interface UserDetailView extends UserSimpleView{}

    private String id;
    private String username;
    private String password;
    private Date birthday;

    @JsonView(UserSimpleView.class)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @JsonView(UserDetailView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
