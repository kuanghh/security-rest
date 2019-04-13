package com.security.rest.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.collect.Lists;
import com.security.rest.dto.User;
import com.security.rest.dto.UserQueryCondition;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


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
