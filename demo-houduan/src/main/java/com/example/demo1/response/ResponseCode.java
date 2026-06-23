package com.example.demo1.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

//lombok 注解-构造函数
@AllArgsConstructor
@Getter
//返回对象
public enum ResponseCode {

    SUCCESS(200,"操作成功"),
    USERNAME_EXIST(1001,"用户名已存在"),
    CAPTCHA_ERROR(1002,"验证码错误"),
    LOGIN_FAIL(1003,"用户名或密码错误"),
    NOT_LOGIN(11012,"您没有登录，请重新登录"),
    TOKEN_INVALID(11011,"登录已失效，请重新登录"),
    ERROR(500,"操作失败");
    //响应状态码
    private Integer code;
    //响应信息
    private String message;
}
