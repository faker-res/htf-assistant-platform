/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon May 27 19:52:26 CST 2019
 * Description:
 */
package com.htf.bigdata.invest.indicatormanage.dao.invest;

import com.htf.bigdata.invest.indicatormanage.model.domain.InvestnewIndexIndicatorStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: investnew_index_indicator_stock
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public interface InvestnewIndexIndicatorStockMapper {
    /**
     * deleteByPrimaryKey
     * @param Long id
     * @return int 
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert
     * @param InvestnewIndexIndicatorStock record
     * @return int 
     */
    int insert(InvestnewIndexIndicatorStock record);

    /**
     * insertSelective
     * @param InvestnewIndexIndicatorStock record
     * @return int 
     */
    int insertSelective(InvestnewIndexIndicatorStock record);

    /**
     * selectByPrimaryKey
     * @param Long id
     * @return InvestnewIndexIndicatorStock 
     */
    InvestnewIndexIndicatorStock selectByPrimaryKey(Long id);

    /**
     * updateByPrimaryKeySelective
     * @param InvestnewIndexIndicatorStock record
     * @return int 
     */
    int updateByPrimaryKeySelective(InvestnewIndexIndicatorStock record);

    /**
     * updateByPrimaryKey
     * @param InvestnewIndexIndicatorStock record
     * @return int 
     */
    int updateByPrimaryKey(InvestnewIndexIndicatorStock record);

    /**
     * 批量插入
     * @param stocks
     * @return
     */
    int insertBatch(@Param("list") List<InvestnewIndexIndicatorStock> stocks);

    /**
     * 查询关联股票
     * @param indicatorId
     * @return
     */
    List<InvestnewIndexIndicatorStock> selectByIndicatorId(Long indicatorId);

    /**
     * 删除关联股票
     * @param indicatorId
     * @return
     */
    int deleteByIndicatorId(Long indicatorId);
}