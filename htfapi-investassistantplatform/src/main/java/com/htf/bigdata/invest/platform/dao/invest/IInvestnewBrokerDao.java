package com.htf.bigdata.invest.platform.dao.invest;

import com.htf.bigdata.invest.platform.model.invest.InvestnewBrokerModel;

import java.util.List;

public interface IInvestnewBrokerDao {
    int deleteByPrimaryKey(Long org_uni_code);

    int insert(InvestnewBrokerModel record);

    int insertSelective(InvestnewBrokerModel record);

    InvestnewBrokerModel selectByPrimaryKey(Long org_uni_code);

    int updateByPrimaryKeySelective(InvestnewBrokerModel record);

    int updateByPrimaryKey(InvestnewBrokerModel record);

    List<InvestnewBrokerModel> getAll();
}