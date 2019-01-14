package com.example.demo.util;

/**
 * @Auther: liuxiangtao90
 * @Date: 2019/1/14 10:22
 * @Description:正则表达式工具类
 */
public class RegexpUtil {

    // 手机号码
    public static final String REGEX_MOBILE = "^[1][0-9]{10}$";

    // 邮箱
    public static final String  REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";}
