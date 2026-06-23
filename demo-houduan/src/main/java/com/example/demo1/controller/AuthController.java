package com.example.demo1.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo1.dto.LoginDTO;
import com.example.demo1.entity.Admin;
import com.example.demo1.exception.BusinessException;
import com.example.demo1.response.ResponseCode;
import com.example.demo1.response.Result;
import com.example.demo1.service.AdminService;
import com.example.demo1.service.CaptchaService;
import com.example.demo1.util.JwtUtil;
import com.example.demo1.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Tag(name = "认证管理")
@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Resource
    private AdminService adminService;

    @Resource
    private CaptchaService captchaService;

    @Operation(summary = "获取验证码")
    @GetMapping("/captcha")
    public Result<Map<String, String>> captcha() throws IOException {
        return Result.data(captchaService.generateCaptcha());
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        captchaService.validate(loginDTO.getCaptchaKey(), loginDTO.getCaptchaCode());

        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername, loginDTO.getUsername());
        Admin admin = adminService.getOne(wrapper);

        if (admin == null || !admin.getUserpwd().equals(loginDTO.getUserpwd())) {
            throw new BusinessException(ResponseCode.LOGIN_FAIL);
        }

        LoginVO vo = new LoginVO();
        vo.setToken(JwtUtil.createToken(admin.getId(), admin.getUsername()));
        vo.setId(admin.getId());
        vo.setUsername(admin.getUsername());
        vo.setName(admin.getName());
        vo.setHeadurl(admin.getHeadurl());
        return Result.data(vo);
    }

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<Void> register(@RequestBody Admin admin) {
        if (!StringUtils.hasText(admin.getUsername()) || !StringUtils.hasText(admin.getUserpwd())) {
            return Result.fail("用户名和密码不能为空");
        }

        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername, admin.getUsername());
        if (adminService.count(wrapper) > 0) {
            throw new BusinessException(ResponseCode.USERNAME_EXIST);
        }

        if (!StringUtils.hasText(admin.getName())) {
            admin.setName(admin.getUsername());
        }
        adminService.save(admin);
        return Result.success("注册成功");
    }
}