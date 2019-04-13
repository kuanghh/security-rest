package com.security.rest.config;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Data
public class MyPasswordEncoderChooser {

    private PasswordEncoder passwordEncoder;

    public MyPasswordEncoderChooser(){
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
}
