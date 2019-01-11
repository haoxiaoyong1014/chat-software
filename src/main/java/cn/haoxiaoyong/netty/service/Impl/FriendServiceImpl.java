package cn.haoxiaoyong.netty.service.Impl;

import cn.haoxiaoyong.netty.mapper.FriendMapper;
import cn.haoxiaoyong.netty.mapper.UserMapper;
import cn.haoxiaoyong.netty.model.FriendReq;
import cn.haoxiaoyong.netty.model.User;
import cn.haoxiaoyong.netty.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by haoxy on 2019/1/11.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Service
@Transactional
public class FriendServiceImpl implements FriendService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FriendMapper friendMapper;

    @Override
    public void addFriend(FriendReq friendReq) {
        //检查是不是添加的自己
        User user = userMapper.selectByPrimaryKey(friendReq.getFromUserid());
        if (user.getId().equals(friendReq.getToUserid())) {
            throw new RuntimeException("不能添加自己为好友");
        }
        //检查是否已经是好友

    }

}
