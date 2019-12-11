package com.htf.bigdata.invest.platform.dao.invest;

import com.htf.bigdata.invest.platform.model.invest.InvestnewUserModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IInvestnewUserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(InvestnewUserModel record);

    int insertSelective(InvestnewUserModel record);

    InvestnewUserModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InvestnewUserModel record);

    int updateByPrimaryKey(InvestnewUserModel record);

    InvestnewUserModel selectByUserId(String userId);

    List<InvestnewUserModel> selectByUserIds(@Param("userIds") List<String> userIds);

    Integer getCount(@Param("companyId") Long companyId, @Param("companyType") String companyType, @Param("keyword") String keyword);

    List<InvestnewUserModel> getByPage(@Param("companyId") Long companyId, @Param("companyType") String companyType,
                                       @Param("keyword") String keyword, @Param("offset") Integer offset, @Param("limit") Integer limit);

    List<Long> getFundCompanyIds();

    List<Long> getBrokerCompanyIds();

    InvestnewUserModel selectByAccount(String account);
}