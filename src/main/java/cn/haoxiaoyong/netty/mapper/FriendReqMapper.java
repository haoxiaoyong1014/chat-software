package cn.haoxiaoyong.netty.mapper;

import cn.haoxiaoyong.netty.model.FriendReq;
import cn.haoxiaoyong.netty.model.FriendReqExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendReqMapper {
    long countByExample(FriendReqExample example);

    int deleteByExample(FriendReqExample example);

    int deleteByPrimaryKey(String id);

    int insert(FriendReq record);

    int insertSelective(FriendReq record);

    List<FriendReq> selectByExample(FriendReqExample example);

    FriendReq selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FriendReq record, @Param("example") FriendReqExample example);

    int updateByExample(@Param("record") FriendReq record, @Param("example") FriendReqExample example);

    int updateByPrimaryKeySelective(FriendReq record);

    int updateByPrimaryKey(FriendReq record);
}