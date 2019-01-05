package cn.haoxiaoyong.netty.controller;

import cn.haoxiaoyong.netty.model.User;
import cn.haoxiaoyong.netty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by haoxy on 2019/1/5.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/selectAll")
    public List<User> selectAll() {
        List<User> users = userService.selectAll();
        return users;
    }
}
