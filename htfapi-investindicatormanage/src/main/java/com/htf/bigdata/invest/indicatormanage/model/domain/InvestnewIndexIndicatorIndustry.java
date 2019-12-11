/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon May 27 19:52:26 CST 2019
 * Description:
 */
package com.htf.bigdata.invest.indicatormanage.model.domain;

import lombok.Data;

/**
 * InvestnewIndexIndicatorIndustry investnew_index_indicator_industry
 */
@Data
public class InvestnewIndexIndicatorIndustry extends BaseDomain {

	/**
	 * 指标ID investnew_index_indicator_industry.indicator_id
	 */
	private Long indicatorId;

	/**
	 * 一级行业名称 investnew_index_indicator_industry.first_industry_name
	 */
	private String firstIndustryName;

	/**
	 * 二级行业名称 investnew_index_indicator_industry.second_industry_name
	 */
	private String secondIndustryName;

}