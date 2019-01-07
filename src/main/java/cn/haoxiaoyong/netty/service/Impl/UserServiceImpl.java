package cn.haoxiaoyong.netty.service.Impl;

import cn.haoxiaoyong.netty.mapper.UserMapper;
import cn.haoxiaoyong.netty.model.User;
import cn.haoxiaoyong.netty.model.UserExample;
import cn.haoxiaoyong.netty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
 * Created by haoxy on 2019/1/5.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectAll() {

        return userMapper.selectByExample(null);
    }

    @Override
    public User login(String username, String password) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(example);
        if (users != null && users.size() == 1) {
            //对密码进行校验
            String encoding = DigestUtils.md5DigestAsHex(password.getBytes());
            if (encoding.equals(users.get(0).getPassword())) {
                return users.get(0);
            }
        }
        return null;
    }

    /**
     * 用户注册
     *
     * @param user
     */
    @Override
    public void register(User user) {

        //要判断这个用户是否存在
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(user.getUsername());
        List<User> users = userMapper.selectByExample(example);
        if (users != null && users.size() > 0) {
            throw new RuntimeException("用户已经存在!");
        }
        //否则将用户信息保存到数据库
        //user.setId();

    }
}
