package com.htf.bigdata.invest.platform.dao.invest;

import com.htf.bigdata.invest.platform.model.invest.InvestnewMessageBroadcastModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IInvestnewMessageBroadcastDao {
    int deleteByPrimaryKey(Long id);

    int insert(InvestnewMessageBroadcastModel record);

    int insertSelective(InvestnewMessageBroadcastModel record);

    InvestnewMessageBroadcastModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InvestnewMessageBroadcastModel record);

    int updateByPrimaryKeyWithBLOBs(InvestnewMessageBroadcastModel record);

    int updateByPrimaryKey(InvestnewMessageBroadcastModel record);

    List<InvestnewMessageBroadcastModel> selectNoCompany(@Param("minimumId") Long minimumId);

    List<InvestnewMessageBroadcastModel> selectByCompany(@Param("minimumId") Long minimumId, @Param("companyId") Long companyId, @Param("companyType") String companyType);
}