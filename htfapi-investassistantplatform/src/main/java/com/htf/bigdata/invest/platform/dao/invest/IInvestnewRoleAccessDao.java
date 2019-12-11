package com.htf.bigdata.invest.platform.dao.invest;

import com.htf.bigdata.invest.platform.model.invest.InvestnewRoleAccessKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IInvestnewRoleAccessDao {
    int deleteByPrimaryKey(InvestnewRoleAccessKey key);

    int insert(InvestnewRoleAccessKey record);

    int insertSelective(InvestnewRoleAccessKey record);

    int insertMulti(@Param("records") List<InvestnewRoleAccessKey> records);

    List<InvestnewRoleAccessKey> selectByRoleIds(@Param("roleIds") List<Integer> roleIds);

    List<String> getAccessIdsByRoleId(@Param("roleId") Integer roleId);

    int deleteByRoleId(Integer roleId);
}