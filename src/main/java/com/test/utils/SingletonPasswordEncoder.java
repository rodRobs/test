package com.test.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SingletonPasswordEncoder {

    private static final SingletonPasswordEncoder instance = new SingletonPasswordEncoder();

    public static SingletonPasswordEncoder getInstance() {
        return instance;
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
