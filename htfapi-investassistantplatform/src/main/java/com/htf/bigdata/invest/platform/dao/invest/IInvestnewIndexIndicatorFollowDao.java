package com.htf.bigdata.invest.platform.dao.invest;

import com.htf.bigdata.invest.platform.model.invest.InvestnewIndexIndicatorFollowModel;
import org.apache.ibatis.annotations.Param;

public interface IInvestnewIndexIndicatorFollowDao {
    int deleteByPrimaryKey(Long id);

    int insert(InvestnewIndexIndicatorFollowModel record);

    int insertSelective(InvestnewIndexIndicatorFollowModel record);

    InvestnewIndexIndicatorFollowModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InvestnewIndexIndicatorFollowModel record);

    int updateByPrimaryKey(InvestnewIndexIndicatorFollowModel record);

    int deleteByObjectIdAndType(@Param("userId") String userId,@Param("objectId") Long objectId,@Param("type") Integer type);
}