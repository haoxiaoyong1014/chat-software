package cn.haoxiaoyong.netty.service;

import cn.haoxiaoyong.netty.model.User;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 用户注册
     * @param user
     */
    void register(User user);

    /**
     * 上传文件: 图片头像
     * @param file
     * @param userid
     * @return
     */
    User upload(MultipartFile file, String userid);
}
