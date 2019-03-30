package com.security.rest.securitydemo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private String id;
    private String username;
    private String password;
    private Date birthday;

}
