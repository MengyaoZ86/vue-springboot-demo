package com.example.demo1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo1.entity.Admin;
import com.example.demo1.mapper.AdminMapper;
import com.example.demo1.service.AdminService;
import org.springframework.stereotype.Service;

//业务代码
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin > implements AdminService {

}
