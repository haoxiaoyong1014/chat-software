package cn.haoxiaoyong.netty.service.Impl;

import cn.haoxiaoyong.netty.mapper.FriendMapper;
import cn.haoxiaoyong.netty.mapper.FriendReqMapper;
import cn.haoxiaoyong.netty.mapper.UserMapper;
import cn.haoxiaoyong.netty.model.*;
import cn.haoxiaoyong.netty.service.FriendService;
import cn.haoxiaoyong.netty.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Autowired
    private FriendReqMapper friendReqMapper;
    @Autowired
    private IdWorker idWorker;

    @Override
    public void addFriend(FriendReq friendReq) {
        //检查是不是添加的自己
        User friend = userMapper.selectByPrimaryKey(friendReq.getToUserid());
        if (friend.getId().equals(friendReq.getFromUserid())) {
            throw new RuntimeException("不能添加自己为好友");
        }
        //检查是否已经是好友,不能重复添加，检查朋友表中是否已经有这两个人的关联信息
        FriendExample example = new FriendExample();
        example.createCriteria().andUseridEqualTo(friendReq.getFromUserid()).andFriendsIdEqualTo(friend.getId());
        List<Friend> friends = friendMapper.selectByExample(example);
        if (friends != null && friends.size() > 0) {
            throw new RuntimeException(friend.getUsername() + "已经是您的好友了");
        }
        //检查是否已经申请过了
        FriendReqExample friendReqexample = new FriendReqExample();
        friendReqexample.createCriteria().andFromUseridEqualTo(friendReq.getFromUserid())
                .andToUseridEqualTo(friend.getId())
                .andStatusEqualTo(0);//0表示还没有进行处理，1表示已经处理
        List<FriendReq> friendReqs = friendReqMapper.selectByExample(friendReqexample);
        if (friendReqs != null && friendReqs.size() > 0) {
            throw new RuntimeException("您已经申请过了,不要进行重复申请");
        }
        friendReq.setId(idWorker.nextId());
        friendReq.setStatus(0);
        friendReq.setCreatetime(new Date());
        friendReqMapper.insert(friendReq);
    }

    @Override
    public List<User> findFriendReqByUserid(String userid) {
        //查询friendReq表,并查询出当前请求我为好友的用户
        FriendReqExample example = new FriendReqExample();
        example.createCriteria().andToUseridEqualTo(userid).andStatusEqualTo(0);
        List<FriendReq> friendReqs = friendReqMapper.selectByExample(example);
        List<User> userList = new ArrayList<>();
        if (friendReqs != null && friendReqs.size() > 0) {
            for (FriendReq friendReq : friendReqs) {
                User user = userMapper.selectByPrimaryKey(friendReq.getFromUserid());
                user.setId(friendReq.getId());//这里之所以要把id设置成friendReqId是因为再进行同意或者忽略的时候需要用到friendReqId
                userList.add(user);
            }
            return userList;
        }
        return null;
    }

    @Override
    public void acceptFriendReq(String reqid) {
        //1,修改friendReq表中的状态值 1:表示已经处理 0：表示没有处理
        FriendReq friendReq = friendReqMapper.selectByPrimaryKey(reqid);
        friendReq.setStatus(1);
        friendReqMapper.updateByPrimaryKey(friendReq);
        //2,向friend表中相互添加彼此
        Friend iFriend = new Friend();
        iFriend.setId(idWorker.nextId());
        iFriend.setUserid(friendReq.getFromUserid());
        iFriend.setFriendsId(friendReq.getToUserid());
        iFriend.setCreatetime(new Date());
        Friend friend = new Friend();
        friend.setId(idWorker.nextId());
        friend.setUserid(friendReq.getToUserid());
        friend.setFriendsId(friendReq.getFromUserid());
        friend.setCreatetime(new Date());
        friendMapper.insert(iFriend);
        friendMapper.insert(friend);
    }

    @Override
    public void ignoreFriendReq(String reqid) {
        FriendReq friendReq = friendReqMapper.selectByPrimaryKey(reqid);
        friendReq.setStatus(1);
        friendReqMapper.updateByPrimaryKey(friendReq);
    }

    @Override
    public List<User> findFriendByUserid(String userid) {
        //根据userid查询friend表
        FriendExample example = new FriendExample();
        example.createCriteria().andUseridEqualTo(userid);
        List<Friend> friends = friendMapper.selectByExample(example);
        if (friends != null && friends.size() > 0) {
            //遍历friends,得到friendId,用friendId将这个用户的信息查询出来
            List<User> friendList = new ArrayList<>();
            for (Friend friend : friends) {
                User user = userMapper.selectByPrimaryKey(friend.getFriendsId());
                friendList.add(user);
            }
            return friendList;
        }
        return null;
    }
}
