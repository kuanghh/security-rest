package com.security.rest.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.collect.Lists;
import com.security.rest.dto.User;
import com.security.rest.dto.UserQueryCondition;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /**
     *回显第三方用户信息
     * @return
     */
    @PostMapping("/showUser")
    public Object showUser(User user, HttpServletRequest request){

        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        String displayName = connection.getDisplayName();//用户唯一标识，openId
        String imageUrl = connection.getImageUrl();//用户头像
        return "success";
    }

    /**
     *注册
     * @return
     */
    @PostMapping("/register")
    public Object register(User user, HttpServletRequest request){
        //进行对业务用户信息 与 第三方应用用户信息的关联
        providerSignInUtils.doPostSignUp(user.getId(), new ServletWebRequest(request));
        return "success";
    }



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
