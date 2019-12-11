package com.htf.bigdata.invest.platform.dao.invest;

import com.htf.bigdata.invest.platform.model.invest.InvestnewUserRoleKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IInvestnewUserRoleDao {
    int deleteByPrimaryKey(InvestnewUserRoleKey key);

    int insert(InvestnewUserRoleKey record);

    int insertSelective(InvestnewUserRoleKey record);

    int insertMulti(@Param("records") List<InvestnewUserRoleKey> records);

    int insertIgnore(InvestnewUserRoleKey record);

    List<Integer> getRoleIdsByUserId(String userId);

    List<InvestnewUserRoleKey> selectByUserIds(@Param("userIds") List<String> userIds);

    int deleteByUserId(String userId);

    int deleteByRoleId(Integer roleId);
}