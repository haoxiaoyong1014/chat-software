package cn.haoxiaoyong.netty.service;

import cn.haoxiaoyong.netty.model.ChatRecord;

import java.util.List;

/**
 * Created by haoxiaoyong on 2019/1/13 下午 4:40
 * e-mail: hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
public interface ChatRecordService {

    void insert(ChatRecord chatRecord);

    List<ChatRecord> findByUserIdAndFriendId(String userid, String friendid);
}
