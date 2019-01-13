package cn.haoxiaoyong.netty.service.Impl;

import cn.haoxiaoyong.netty.mapper.ChatRecordMapper;
import cn.haoxiaoyong.netty.model.ChatRecord;
import cn.haoxiaoyong.netty.model.ChatRecordExample;
import cn.haoxiaoyong.netty.service.ChatRecordService;
import cn.haoxiaoyong.netty.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by haoxiaoyong on 2019/1/13 下午 4:41
 * e-mail: hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@Service
@Transactional
public class ChatRecordServiceImpl implements ChatRecordService {
    @Autowired
    private ChatRecordMapper chatRecordMapper;
    @Autowired
    private IdWorker idWorker;

    @Override
    public void insert(ChatRecord chatRecord) {
        chatRecord.setId(idWorker.nextId());
        chatRecord.setHasDelete(0);//是否删除
        chatRecord.setHasRead(0);//是否已读
        chatRecord.setCreatetime(new Date());
        chatRecordMapper.insert(chatRecord);
    }

    @Override
    public List<ChatRecord> findByUserIdAndFriendId(String userid, String friendid) {
        ChatRecordExample example = new ChatRecordExample();
        ChatRecordExample.Criteria criteria1 = example.createCriteria();
        ChatRecordExample.Criteria criteria2 = example.createCriteria();
        //查询出 userid->friendid 的消息
        criteria1.andUseridEqualTo(userid);
        criteria1.andFriendidEqualTo(friendid);
        criteria1.andHasDeleteEqualTo(0);
        //查询出 friendid->userid 的消息
        criteria2.andUseridEqualTo(friendid);
        criteria2.andFriendidEqualTo(userid);
        criteria2.andHasDeleteEqualTo(0);
        example.or(criteria1);
        example.or(criteria2);
        return chatRecordMapper.selectByExample(example);
    }
}
