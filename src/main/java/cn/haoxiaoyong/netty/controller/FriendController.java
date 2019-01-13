package cn.haoxiaoyong.netty.controller;

import cn.haoxiaoyong.netty.entity.Result;
import cn.haoxiaoyong.netty.model.FriendReq;
import cn.haoxiaoyong.netty.model.User;
import cn.haoxiaoyong.netty.service.FriendService;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.scene.chart.ValueAxis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by haoxy on 2019/1/11.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@RestController
@RequestMapping(value = "friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @RequestMapping(value = "sendRequest")
    public Result addFriend(@RequestBody FriendReq friendReq) {
        try {
            friendService.addFriend(friendReq);
            return new Result(true, "等待用户同意");
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加好友失败");
        }
    }

    @RequestMapping(value = "findFriendReqByUserid")
    public List<User> findFriendReqByUserid(String userid) {
        return friendService.findFriendReqByUserid(userid);
    }
    @RequestMapping(value = "acceptFriendReq")
    public Result acceptFriendReq(String reqid){
        try {
            friendService.acceptFriendReq(reqid);
            return new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加好友异常");
        }
    }
    @RequestMapping(value = "ignoreFriendReq")
    public Result ignoreFriendReq(String reqid){
        try {
            friendService.ignoreFriendReq(reqid);
           return new Result(true,"以拒绝");
        } catch (Exception e) {
            e.printStackTrace();
           return new Result(false,"拒绝异常");
        }
    }
    @RequestMapping(value = "findFriendByUserid")
    public List<User> findFriendByUserid(String userid){
       return friendService.findFriendByUserid(userid);
    }
}