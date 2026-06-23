package com.example.demo1.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "登录响应")
public class LoginVO {

    private String token;
    private Long id;
    private String username;
    private String name;
    private String headurl;
}
