package com.htf.bigdata.invest.platform.dao.invest;

import com.htf.bigdata.invest.platform.model.invest.InvestnewFundModel;

import java.util.List;

public interface IInvestnewFundDao {
    int deleteByPrimaryKey(Long org_uni_code);

    int insert(InvestnewFundModel record);

    int insertSelective(InvestnewFundModel record);

    InvestnewFundModel selectByPrimaryKey(Long org_uni_code);

    int updateByPrimaryKeySelective(InvestnewFundModel record);

    int updateByPrimaryKeyWithBLOBs(InvestnewFundModel record);

    int updateByPrimaryKey(InvestnewFundModel record);

    List<InvestnewFundModel> getAll();
}