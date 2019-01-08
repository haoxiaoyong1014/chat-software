package cn.haoxiaoyong.netty.controller;

import cn.haoxiaoyong.netty.entity.Result;
import cn.haoxiaoyong.netty.model.User;
import cn.haoxiaoyong.netty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping(value = "login")
    public Result login(@RequestBody User user) {

        try {
            User userInfo = userService.login(user.getUsername(), user.getPassword());
            if (userInfo == null) {
                return new Result(false, "登录失败,请检查用户名和密码是否正确");
            } else {
                return new Result(true, "登录成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "登录失败");
        }
    }

    @RequestMapping(value = "register")
    public Result register(@RequestBody User user) {
        try {
            userService.register(user);
            return new Result(true, "注册成功");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }
    }

}
