package cn.haoxiaoyong.netty.controller;

import cn.haoxiaoyong.netty.entity.Result;
import cn.haoxiaoyong.netty.model.User;
import cn.haoxiaoyong.netty.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
                return new Result(true, "登录成功", userInfo);
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

    @RequestMapping(value = "upload")
    public Result upload(@RequestParam("file") MultipartFile file, @RequestParam("userid") String userid) {

        try {
            User user = userService.upload(file, userid);
            if (user != null) {
                System.out.println(user);
                return new Result(true, "上传成功", user);
            } else {
                return new Result(false, "上传失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "上传失败");
        }
    }

    @RequestMapping("updateNickname")
    public Result updateNickName(@RequestBody User user) {

        try {
            userService.updateNickName(user.getId(), user.getNickname());
            return new Result(true, "更新成功");
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "更新失败");
        }
    }

    @RequestMapping("findById")
    public Result reloadUser(String userid) {
        try {
            User user = userService.reloadUser(userid);
            if (user != null) {
                return new Result(true, "搜索成功", user);
            }
            return new Result(false, "查询的用户不存在");
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询失败");
        }
    }
}
