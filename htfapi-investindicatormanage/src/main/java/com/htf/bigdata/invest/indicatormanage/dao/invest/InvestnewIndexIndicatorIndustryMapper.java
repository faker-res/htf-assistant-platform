/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon May 27 19:52:26 CST 2019
 * Description:
 */
package com.htf.bigdata.invest.indicatormanage.dao.invest;

import com.htf.bigdata.invest.indicatormanage.model.domain.InvestnewIndexIndicatorIndustry;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: investnew_index_indicator_industry
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public interface InvestnewIndexIndicatorIndustryMapper {
    /**
     * deleteByPrimaryKey
     * @param Long id
     * @return int 
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert
     * @param InvestnewIndexIndicatorIndustry record
     * @return int 
     */
    int insert(InvestnewIndexIndicatorIndustry record);

    /**
     * insertSelective
     * @param InvestnewIndexIndicatorIndustry record
     * @return int 
     */
    int insertSelective(InvestnewIndexIndicatorIndustry record);

    /**
     * selectByPrimaryKey
     * @param Long id
     * @return InvestnewIndexIndicatorIndustry 
     */
    InvestnewIndexIndicatorIndustry selectByPrimaryKey(Long id);

    /**
     * updateByPrimaryKeySelective
     * @param InvestnewIndexIndicatorIndustry record
     * @return int 
     */
    int updateByPrimaryKeySelective(InvestnewIndexIndicatorIndustry record);

    /**
     * updateByPrimaryKey
     * @param InvestnewIndexIndicatorIndustry record
     * @return int 
     */
    int updateByPrimaryKey(InvestnewIndexIndicatorIndustry record);

    /**
     * 批量插入
     * @param industries
     * @return
     */
    int insertBatch(@Param("list") List<InvestnewIndexIndicatorIndustry> industries);

    /**
     * 查询指标关联的行业
     * @param indicatorId
     * @return
     * @throws Exception
     */
    List<InvestnewIndexIndicatorIndustry> selectByIndicatorId(Long indicatorId);
}