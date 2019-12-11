package com.htf.bigdata.invest.indicatormanage.dao.invest;

import com.htf.bigdata.invest.indicatormanage.model.domain.InvestnewIndexIndicatorText;

public interface InvestnewIndexIndicatorTextMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InvestnewIndexIndicatorText record);

    int insertSelective(InvestnewIndexIndicatorText record);

    InvestnewIndexIndicatorText selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InvestnewIndexIndicatorText record);

    int updateByPrimaryKeyWithBLOBs(InvestnewIndexIndicatorText record);

    int updateByPrimaryKey(InvestnewIndexIndicatorText record);

    InvestnewIndexIndicatorText selectByIndicatorId(Long indicatorId);
}