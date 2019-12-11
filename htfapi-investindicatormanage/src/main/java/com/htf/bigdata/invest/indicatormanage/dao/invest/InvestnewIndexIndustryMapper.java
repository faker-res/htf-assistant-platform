/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon May 27 19:52:26 CST 2019
 * Description:
 */
package com.htf.bigdata.invest.indicatormanage.dao.invest;

import com.htf.bigdata.invest.indicatormanage.model.domain.InvestnewIndexIndustry;

import java.util.List;

/**
 * @description: investnew_index_industry
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public interface InvestnewIndexIndustryMapper {
    /**
     * deleteByPrimaryKey
     * @param Long id
     * @return int 
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert
     * @param InvestnewIndexIndustry record
     * @return int 
     */
    int insert(InvestnewIndexIndustry record);

    /**
     * insertSelective
     * @param InvestnewIndexIndustry record
     * @return int 
     */
    int insertSelective(InvestnewIndexIndustry record);

    /**
     * selectByPrimaryKey
     * @param Long id
     * @return InvestnewIndexIndustry 
     */
    InvestnewIndexIndustry selectByPrimaryKey(Long id);

    /**
     * updateByPrimaryKeySelective
     * @param InvestnewIndexIndustry record
     * @return int 
     */
    int updateByPrimaryKeySelective(InvestnewIndexIndustry record);

    /**
     * updateByPrimaryKey
     * @param InvestnewIndexIndustry record
     * @return int 
     */
    int updateByPrimaryKey(InvestnewIndexIndustry record);

    /**
     * 查询父节点下的行业
     * @param parentId
     * @return
     */
    List<InvestnewIndexIndustry> selectIndustryByParentId(Long parentId);

    /**
     * 根据名称查询二级行业
     * @param industryName
     * @return
     */
    InvestnewIndexIndustry selectSecondByIndustry(String industryName);
}