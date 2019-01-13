package cn.haoxiaoyong.netty.service;

import cn.haoxiaoyong.netty.model.FriendReq;
import cn.haoxiaoyong.netty.model.User;

import java.util.List;

/**
 * Created by haoxy on 2019/1/11.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public interface FriendService {

    /**
     * 点击添加好友
     * @param friendReq
     */
    void addFriend(FriendReq friendReq);

    /**
     * @param userid：当前用户id
     * @return 请求当前用户为好友的用户集合
     */
    List<User> findFriendReqByUserid(String userid);

    /**
     * 同意好友的请求
     * @param reqid：请求id
     */
    void acceptFriendReq(String reqid);

    /**
     * 拒绝好友的请求
     * @param reqid：请求id
     */
    void ignoreFriendReq(String reqid);

    /**
     * 通讯录列表页面
     * @param userid：当前用户id
     * @return
     */
    List<User> findFriendByUserid(String userid);
}
