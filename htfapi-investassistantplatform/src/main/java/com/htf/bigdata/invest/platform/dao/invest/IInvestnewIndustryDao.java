package com.htf.bigdata.invest.platform.dao.invest;

import com.htf.bigdata.invest.platform.model.invest.InvestnewIndustryModel;

import java.util.List;

public interface IInvestnewIndustryDao {
    int deleteByPrimaryKey(Long index_uni_code);

    int insert(InvestnewIndustryModel record);

    int insertSelective(InvestnewIndustryModel record);

    InvestnewIndustryModel selectByPrimaryKey(Long index_uni_code);

    int updateByPrimaryKeySelective(InvestnewIndustryModel record);

    int updateByPrimaryKey(InvestnewIndustryModel record);

    List<InvestnewIndustryModel> getAll();
}