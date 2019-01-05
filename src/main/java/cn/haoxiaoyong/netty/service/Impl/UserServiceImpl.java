package cn.haoxiaoyong.netty.service.Impl;

import cn.haoxiaoyong.netty.mapper.UserMapper;
import cn.haoxiaoyong.netty.model.User;
import cn.haoxiaoyong.netty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
