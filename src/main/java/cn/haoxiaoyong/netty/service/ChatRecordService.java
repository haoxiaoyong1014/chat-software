package cn.haoxiaoyong.netty.service;

import cn.haoxiaoyong.netty.model.ChatRecord;
import cn.haoxiaoyong.netty.model.User;

import java.util.List;

/**
 * Created by haoxiaoyong on 2019/1/13 下午 4:40
 * e-mail: hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
public interface ChatRecordService {

    void insert(ChatRecord chatRecord);

    /**
     * 查询消息内容
     * @param userid
     * @param friendid
     * @return
     */
    List<ChatRecord> findByUserIdAndFriendId(String userid, String friendid);

    /**
     * 查询未读消息
     * @param userid
     * @return
     */
    List<ChatRecord> findUnreadByUserid(String userid);

    /**
     * 将消息设置为已读
     * @param id
     */
    void updateChatRecordHasRead(String id);
}
