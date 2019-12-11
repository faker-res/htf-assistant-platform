/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon May 27 19:52:26 CST 2019
 * Description:
 */
package com.htf.bigdata.invest.indicatormanage.dao.invest;

import com.htf.bigdata.invest.indicatormanage.model.domain.InvestnewIndexStock;
import com.htf.bigdata.invest.indicatormanage.model.response.dto.StockDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: investnew_index_stock
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public interface InvestnewIndexStockMapper {
    /**
     * deleteByPrimaryKey
     * @param Long id
     * @return int 
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert
     * @param InvestnewIndexStock record
     * @return int 
     */
    int insert(InvestnewIndexStock record);

    /**
     * insertSelective
     * @param InvestnewIndexStock record
     * @return int 
     */
    int insertSelective(InvestnewIndexStock record);

    /**
     * selectByPrimaryKey
     * @param Long id
     * @return InvestnewIndexStock 
     */
    InvestnewIndexStock selectByPrimaryKey(Long id);

    /**
     * updateByPrimaryKeySelective
     * @param InvestnewIndexStock record
     * @return int 
     */
    int updateByPrimaryKeySelective(InvestnewIndexStock record);

    /**
     * updateByPrimaryKey
     * @param InvestnewIndexStock record
     * @return int 
     */
    int updateByPrimaryKey(InvestnewIndexStock record);

    /**
     * 根据关键字查询股票
     * @param keyword
     * @param limit
     * @return
     */
    List<StockDto> selectByKeywordAndLimit(@Param("keyword") String keyword, @Param("limit") Integer limit);

    /**
     * 根据股票code和stockName查询股票
     * @param stockCode
     * @param stockName
     * @return
     */
    InvestnewIndexStock selectByCodeAndName(@Param("stockCode") String stockCode, @Param("stockName") String stockName);
}