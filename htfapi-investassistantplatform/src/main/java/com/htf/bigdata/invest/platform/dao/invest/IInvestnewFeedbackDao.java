package com.htf.bigdata.invest.platform.dao.invest;

import com.htf.bigdata.invest.platform.model.invest.InvestnewFeedbackModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IInvestnewFeedbackDao {
    int deleteByPrimaryKey(Long id);

    int insert(InvestnewFeedbackModel record);

    int insertSelective(InvestnewFeedbackModel record);

    InvestnewFeedbackModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InvestnewFeedbackModel record);

    int updateByPrimaryKeyWithBLOBs(InvestnewFeedbackModel record);

    int updateByPrimaryKey(InvestnewFeedbackModel record);

    List<InvestnewFeedbackModel> getList(@Param("offset") Integer offset,@Param("limit") Integer limit);

    int count();
}