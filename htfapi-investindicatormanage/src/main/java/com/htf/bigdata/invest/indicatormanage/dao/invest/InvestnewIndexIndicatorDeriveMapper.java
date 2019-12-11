/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Thu May 30 20:07:18 CST 2019
 * Description:
 */
package com.htf.bigdata.invest.indicatormanage.dao.invest;

import org.apache.ibatis.annotations.Param;

import com.htf.bigdata.invest.indicatormanage.model.domain.InvestnewIndexIndicatorDerive;

import java.util.List;
import java.util.Map;

/**
 * @description: investnew_index_indicator_derive
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public interface InvestnewIndexIndicatorDeriveMapper {
    /**
     * deleteByPrimaryKey
     * @param Long id
     * @return int 
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert
     * @param InvestnewIndexIndicatorDerive record
     * @return int 
     */
    int insert(InvestnewIndexIndicatorDerive record);

    /**
     * insertSelective
     * @param InvestnewIndexIndicatorDerive record
     * @return int 
     */
    int insertSelective(InvestnewIndexIndicatorDerive record);

    /**
     * selectByPrimaryKey
     * @param Long id
     * @return InvestnewIndexIndicatorDerive 
     */
    InvestnewIndexIndicatorDerive selectByPrimaryKey(Long id);

    /**
     * updateByPrimaryKeySelective
     * @param InvestnewIndexIndicatorDerive record
     * @return int 
     */
    int updateByPrimaryKeySelective(InvestnewIndexIndicatorDerive record);

    /**
     * updateByPrimaryKey
     * @param InvestnewIndexIndicatorDerive record
     * @return int 
     */
    int updateByPrimaryKey(InvestnewIndexIndicatorDerive record);
}