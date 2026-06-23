package com.example.demo1.exception;

import com.example.demo1.response.ResponseCode;
import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    //业务 自定义异常
    //错误码 异常信息
    private Integer code;
    private String message;

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message) {
        this.message = message;
    }
    public BusinessException(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }
}