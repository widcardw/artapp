package com.example.springdemo.controller;


import org.junit.Test;

import java.util.regex.Pattern;

public class NameValidater {


    public static boolean usernameValid(String username) {
        String pattern = "[A-Za-z0-9_]{1,18}";
        return Pattern.matches(pattern, username);
    }

    public static boolean nickNameValid(String nickName) {
        String pattern = "[\\w\\d\\u4e00-\\u9fa5_]{1,18}";
        return Pattern.matches(pattern, nickName);
    }

    @Test
    public void testUsername() {
        boolean b = usernameValid("njnu1919");
        System.out.println("username: " + b);
    }

    @Test
    public void testNickName() {
        boolean b = nickNameValid("啊_你好0");
        System.out.println("nickName: " + b);
    }
}
