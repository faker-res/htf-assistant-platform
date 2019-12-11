package com.htf.bigdata.invest.platform.dao.invest;

import com.htf.bigdata.invest.platform.model.invest.InvestnewAccessModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IInvestnewAccessDao {
    int deleteByPrimaryKey(Integer id);

    int insert(InvestnewAccessModel record);

    int insertSelective(InvestnewAccessModel record);

    InvestnewAccessModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InvestnewAccessModel record);

    int updateByPrimaryKey(InvestnewAccessModel record);

    int insertMulti(@Param("records") List<InvestnewAccessModel> records);

    int deleteByCompanyId(@Param("companyId") Long companyId);

    List<InvestnewAccessModel> selectByCompanyId(Long companyId);

    List<InvestnewAccessModel> selectByAccessIds(@Param("companyId") Long companyId, @Param("accessIds") List<String> accessIds);
}