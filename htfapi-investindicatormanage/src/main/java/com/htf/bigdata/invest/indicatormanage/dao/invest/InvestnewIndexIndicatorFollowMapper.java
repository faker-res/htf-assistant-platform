/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon May 27 19:52:26 CST 2019
 * Description:
 */
package com.htf.bigdata.invest.indicatormanage.dao.invest;

import com.htf.bigdata.invest.indicatormanage.model.domain.InvestnewIndexIndicatorFollow;

/**
 * @description: investnew_index_indicator_follow
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public interface InvestnewIndexIndicatorFollowMapper {
    /**
     * deleteByPrimaryKey
     * @param Long id
     * @return int 
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert
     * @param InvestnewIndexIndicatorFollow record
     * @return int 
     */
    int insert(InvestnewIndexIndicatorFollow record);

    /**
     * insertSelective
     * @param InvestnewIndexIndicatorFollow record
     * @return int 
     */
    int insertSelective(InvestnewIndexIndicatorFollow record);

    /**
     * selectByPrimaryKey
     * @param Long id
     * @return InvestnewIndexIndicatorFollow 
     */
    InvestnewIndexIndicatorFollow selectByPrimaryKey(Long id);

    /**
     * updateByPrimaryKeySelective
     * @param InvestnewIndexIndicatorFollow record
     * @return int 
     */
    int updateByPrimaryKeySelective(InvestnewIndexIndicatorFollow record);

    /**
     * updateByPrimaryKey
     * @param InvestnewIndexIndicatorFollow record
     * @return int 
     */
    int updateByPrimaryKey(InvestnewIndexIndicatorFollow record);
}