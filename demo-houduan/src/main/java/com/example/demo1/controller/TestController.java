package com.example.demo1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//它让这个类变成“专门返回数据”的控制器
//@Controller 前端web控制 +@ResponseBody 返回http响应体
@RestController
//这里也可以注解一层url，比如admin登录
public class TestController {
    @RequestMapping ("/sayHello") // 绑定浏览器访问的路径
    public String sayHello(@RequestParam String name) {
        // @RequestParam 用于接收 URL 中 ?name=xxx 的参数
        return "Hello2 " + name; // 将拼接后的字符串返回给浏览器
    }
}
