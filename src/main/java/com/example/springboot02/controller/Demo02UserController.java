package com.example.springboot02.controller;

import com.example.springboot02.bean.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by Administrator on 2018/9/10.
 */
@RestController
@RequestMapping(value = "user")
public class Demo02UserController {

    @RequestMapping("test_json")
    public User testJson() {
        User user = new User();
        user.setUserId(123);
        user.setUsername(null);
        user.setPassword("00000");
        user.setAge(12);
        user.setCreateTime(new Date());
        return user;
    }
}
