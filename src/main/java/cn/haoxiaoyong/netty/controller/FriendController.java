package cn.haoxiaoyong.netty.controller;

import cn.haoxiaoyong.netty.entity.Result;
import cn.haoxiaoyong.netty.model.FriendReq;
import cn.haoxiaoyong.netty.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
