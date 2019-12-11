package com.htf.bigdata.invest.indicatormanage.dao.invest;

import com.htf.bigdata.invest.indicatormanage.model.domain.InvestnewIndexGroupPublish;
import com.htf.bigdata.invest.indicatormanage.model.domain.InvestnewIndexGroupPublishExample;

import java.util.List;

public interface IInvestnewIndexGroupPublishMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InvestnewIndexGroupPublish record);

    int insertSelective(InvestnewIndexGroupPublish record);

    InvestnewIndexGroupPublish selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InvestnewIndexGroupPublish record);

    int updateByPrimaryKeyWithBLOBs(InvestnewIndexGroupPublish record);

    int updateByPrimaryKey(InvestnewIndexGroupPublish record);

    int deleteByExample(InvestnewIndexGroupPublishExample example);

    List<InvestnewIndexGroupPublish> selectByExampleWithBLOBs(InvestnewIndexGroupPublishExample example);
}