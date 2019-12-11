package com.htf.bigdata.invest.platform.dao.invest;

import com.htf.bigdata.invest.platform.model.invest.InvestnewRoleModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IInvestnewRoleDao {
    int deleteByPrimaryKey(Integer role_id);

    int insert(InvestnewRoleModel record);

    int insertSelective(InvestnewRoleModel record);

    InvestnewRoleModel selectByPrimaryKey(Integer role_id);

    int updateByPrimaryKeySelective(InvestnewRoleModel record);

    int updateByPrimaryKey(InvestnewRoleModel record);

    List<InvestnewRoleModel> selectByCompanyId(Long companyId);

    InvestnewRoleModel selectByName(@Param("companyId") Long companyId, @Param("name") String name);

    List<InvestnewRoleModel> selectByRoleIds(@Param("roleIds") List<Integer> roleIds);

    int insertSelectiveSelectId(InvestnewRoleModel record);
}