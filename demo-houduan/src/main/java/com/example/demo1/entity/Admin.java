package com.example.demo1.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//加一个Lombok的注解，就不用generate get set方法了
//给实体类 加一个表admin的注解 指明这个类对应的数据库表是哪个

@Data
@TableName("admin")
@Schema(description = "管理员信息实体表")
public class Admin {
    //实体表
    //主键 id 自增长主键
    @TableId(type = IdType.AUTO )
    private Long id;
    //用户名 密码 姓名 性别 电话 头像
    //告诉它 和表里username对应，如果一样可以省略
    @TableField("username")
    @Schema(description = "用户名")
    private String username;
    private String userpwd;
    private String name;
    private String sex;
    private String tel;
    private String headurl;

}
