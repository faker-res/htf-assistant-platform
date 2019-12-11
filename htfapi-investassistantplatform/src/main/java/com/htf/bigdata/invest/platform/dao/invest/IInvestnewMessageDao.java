package com.htf.bigdata.invest.platform.dao.invest;

import com.htf.bigdata.invest.platform.model.invest.InvestnewMessageModel;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface IInvestnewMessageDao {
    int deleteByPrimaryKey(Long id);

    int insert(InvestnewMessageModel record);

    int insertSelective(InvestnewMessageModel record);

    InvestnewMessageModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InvestnewMessageModel record);

    int updateByPrimaryKeyWithBLOBs(InvestnewMessageModel record);

    int updateByPrimaryKey(InvestnewMessageModel record);

    List<InvestnewMessageModel> selectByReceiverUserId(@Param("userId") String userId);

    int countByReceiverUserIdUnread(@Param("userId") String userId,@Param("objectType") String objectType, @Param("endTime") Date endTime,
                                    @Param("offset") Integer offset, @Param("limit") Integer limit);

    List<InvestnewMessageModel> selectByReceiverUserIdUnread(@Param("userId") String userId,@Param("objectType") String objectType, @Param("endTime") Date endTime,
                                                             @Param("offset") Integer offset, @Param("limit") Integer limit);

    InvestnewMessageModel getLastBroadcastByReceiverUserId(String userId);

    int setRevoke(@Param("broadcastMessageId") Long broadcastMessageId);

    int setReadByUserId(String userId);

    int setReadByMessageIds(@Param("messageIds") List<Long> messageIds);
}