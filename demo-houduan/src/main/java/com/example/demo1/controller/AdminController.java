package com.example.demo1.controller;

import com.example.demo1.entity.Admin;
import com.example.demo1.exception.BusinessException;
import com.example.demo1.response.ResponseCode;
import com.example.demo1.response.Result;
import com.example.demo1.service.AdminService;
//import io.swagger.annotations.Tag;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
//import jakarta.annotation.Resources;
import lombok.Data;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
/*
entity实体
mapper/Dao 写sql
service 业务代码
controller 前端调用
 */
@Data
//Controller层 给前端调用
@Tag(name="管理员信息管理")
//加个注解，让它可以接受gdp请求
@RestController
public class AdminController {
    @Resource
    private AdminService adminService;

    /*
     * 是否 成功
     * 成功 返回数据
     * 失败 给提示 错误信息
     *
     * */
    @Operation(summary = "新增管理员")
    @PostMapping("/admin/add")
    @CrossOrigin
    public Result add(@RequestBody Admin admin) {
        if (!StringUtils.hasText(admin.getUsername()) || !StringUtils.hasText(admin.getUserpwd())) {
            return Result.fail("用户名和密码不能为空");
        }
        LambdaQueryWrapper<Admin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Admin::getUsername,admin.getUsername());
        long count = adminService.count(lambdaQueryWrapper);
        if (count > 0) {
            throw new BusinessException(ResponseCode.USERNAME_EXIST);
        }
        adminService.save(admin);
        //return admin.getId();
        return Result.success();
    }

    //管理员列表
    //@GetMapping("/admin/list")
    /*
    public Result<List<Admin>> list(){
        //return adminService.list()
        PageHelper.startPage(1,2);
        List<Admin> list = adminService.list();
        return Result.data(list);

    }*/
    @Operation(summary = "管理员列表")
    @PostMapping("/admin/list")
    @CrossOrigin
    public Result<IPage<Admin>> list(@RequestBody Admin admin,
                                     @RequestParam(defaultValue = "1") Long pageNum,
                                     @RequestParam(defaultValue = "10") Long pageSize) {

        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(admin.getName()), Admin::getName, admin.getName());
        wrapper.like(StringUtils.hasText(admin.getTel()), Admin::getTel, admin.getTel());
        wrapper.orderByDesc(Admin::getId);
        Page<Admin> page = new Page<>(pageNum, pageSize);
        IPage<Admin> resultPage = adminService.page(page, wrapper);  // MyBatis-Plus 自带分页
        return Result.data(resultPage);
    }

    @Operation(summary="修改管理员")
    @PostMapping("/admin/update")
    @CrossOrigin
    public Result update(@RequestBody Admin admin){
        if (admin.getId() == null) {
            return Result.fail("缺少用户ID");
        }
        adminService.updateById(admin) ;
        //return admin.getId()
        return Result.success();
    }
    @Operation(summary="删除管理员")
    @PostMapping("/admin/delete")
    @CrossOrigin
    public Result delete(@RequestParam Long id){
        adminService.removeById(id) ;
        return Result.success();
    }
}