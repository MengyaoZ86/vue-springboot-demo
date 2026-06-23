package com.example.demo1.response;

import lombok.Getter;

@Getter
//统一返回结果
public class Result <T>{
    //状态码
    private Integer code;
    //状态描述
    private String message;
    //返回数据 自定义类型 泛型
    private T data;

    private Result(Integer code) {
        this.code = code;
    }

    private Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T>  success() {
        return new Result<>(ResponseCode.SUCCESS.getCode());
    }
    public static <T> Result<T> success(String message) {
        return new Result<>(ResponseCode.SUCCESS.getCode(), message);
    }
    public static <T> Result<T> data(T data) {
        return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> fail() {
        return new Result<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
    }
    public static <T> Result<T> fail(String message) {
        return new Result<>(ResponseCode.ERROR.getCode(), message);
    }
    public static <T> Result<T> fail(ResponseCode responseCode) {
        return new Result<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
    }
    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<>(code,message);
    }
}
