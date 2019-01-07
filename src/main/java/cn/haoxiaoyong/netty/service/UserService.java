package cn.haoxiaoyong.netty.service;

import cn.haoxiaoyong.netty.model.User;

import java.util.List;

/**
 * Created by haoxy on 2019/1/5.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public interface UserService {
    /**
     * 查询所有的 user对象
     * @return
     */
    List<User> selectAll();

    /**
     * 用作登录
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password);

    void register(User user);
}
