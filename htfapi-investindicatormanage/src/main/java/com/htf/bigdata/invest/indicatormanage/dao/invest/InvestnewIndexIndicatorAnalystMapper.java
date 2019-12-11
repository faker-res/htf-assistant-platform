/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon May 27 19:52:26 CST 2019
 * Description:
 */
package com.htf.bigdata.invest.indicatormanage.dao.invest;

import org.apache.ibatis.annotations.Param;

import com.htf.bigdata.invest.indicatormanage.model.domain.InvestnewIndexIndicatorAnalyst;

import java.util.List;

/**
 * @description: investnew_index_indicator_analyst
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public interface InvestnewIndexIndicatorAnalystMapper {
    /**
     * deleteByPrimaryKey
     * @param Long id
     * @return int 
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert
     * @param InvestnewIndexIndicatorAnalyst record
     * @return int 
     */
    int insert(InvestnewIndexIndicatorAnalyst record);

    /**
     * insertSelective
     * @param InvestnewIndexIndicatorAnalyst record
     * @return int 
     */
    int insertSelective(InvestnewIndexIndicatorAnalyst record);

    /**
     * selectByPrimaryKey
     * @param Long id
     * @return InvestnewIndexIndicatorAnalyst 
     */
    InvestnewIndexIndicatorAnalyst selectByPrimaryKey(Long id);

    /**
     * updateByPrimaryKeySelective
     * @param InvestnewIndexIndicatorAnalyst record
     * @return int 
     */
    int updateByPrimaryKeySelective(InvestnewIndexIndicatorAnalyst record);

    /**
     * updateByPrimaryKey
     * @param InvestnewIndexIndicatorAnalyst record
     * @return int 
     */
    int updateByPrimaryKey(InvestnewIndexIndicatorAnalyst record);

    /**
     * 批量插入
     * @param analysts
     * @return
     */
    int insertBatch(@Param("list") List<InvestnewIndexIndicatorAnalyst> analysts);

    InvestnewIndexIndicatorAnalyst selectByIndicatorIdAndAnalystId(@Param("indicatorId") Long indicatorId, @Param("analystId") String analystId);

    List<InvestnewIndexIndicatorAnalyst> selectByIndicatorId(@Param("indicatorId") Long indicatorId);
}