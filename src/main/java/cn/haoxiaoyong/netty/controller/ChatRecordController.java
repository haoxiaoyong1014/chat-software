package cn.haoxiaoyong.netty.controller;

import cn.haoxiaoyong.netty.model.ChatRecord;
import cn.haoxiaoyong.netty.model.User;
import cn.haoxiaoyong.netty.service.ChatRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by haoxiaoyong on 2019/1/13 下午 5:20
 * e-mail: hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@RestController
@RequestMapping(value = "chatrecord")
public class ChatRecordController {

    @Autowired
    private ChatRecordService chatRecordService;

    @RequestMapping(value = "findByUserIdAndFriendId")
    public List<ChatRecord> findByUserIdAndFriendId(String userid, String friendid) {

        return chatRecordService.findByUserIdAndFriendId(userid, friendid);
    }

    @RequestMapping(value = "findUnreadByUserid")
    public List<ChatRecord> findUnreadByUserid(String userid) {

        try {
            return chatRecordService.findUnreadByUserid(userid);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
