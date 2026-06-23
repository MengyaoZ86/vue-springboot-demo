package com.example.demo1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "登录请求")
public class LoginDTO {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String userpwd;

    @Schema(description = "验证码")
    private String captchaCode;

    @Schema(description = "验证码Key")
    private String captchaKey;
}
