package com.security.rest.vo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class UserDetailVO implements UserDetails {

    private Long id;
    private String username;
    private Integer age;
    private String pass;
    private Integer status = 1;

    public UserDetailVO(){

    }

    public UserDetailVO(String username, String pass) {
        this.username = username;
        this.pass = pass;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  AuthorityUtils.createAuthorityList("admin");
    }

    @Override
    public String getPassword() {
        return pass;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status == 1;
    }
}
