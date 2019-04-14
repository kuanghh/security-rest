package com.security.rest.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.collect.Lists;
import com.security.rest.dto.User;
import com.security.rest.dto.UserQueryCondition;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 返回用户登陆输入信息 + 用户的UserDetails信息即认证时的UserDetails
     * @return
     */
    @GetMapping("/me1")
    public Object getUser1(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 同上
     * @param authentication
     * @return
     */
    @GetMapping("/me2")
    public Object getUser2(Authentication authentication){
        return authentication;
    }

    /**
     * 只返回用户的UserDetails信息即认证时的UserDetails
     * @param user
     * @return
     */
    @GetMapping("/me3")
    public Object getUser2(@AuthenticationPrincipal UserDetails user){
        return user;
    }


    @GetMapping
    @JsonView(User.UserDetailView.class)
    public List<User> query(UserQueryCondition condition,
                            @PageableDefault(page = 1, size = 10, sort = "username,asc") Pageable pageable) {

        System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));

        List<User> users = Lists.newArrayList();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }


}
