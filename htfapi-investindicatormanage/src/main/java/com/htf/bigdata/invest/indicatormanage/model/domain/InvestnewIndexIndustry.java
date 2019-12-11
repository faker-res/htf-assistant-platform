/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon May 27 19:52:26 CST 2019
 * Description:
 */
package com.htf.bigdata.invest.indicatormanage.model.domain;

import lombok.Data;

/**
 * InvestnewIndexIndustry investnew_index_industry
 */
@Data
public class InvestnewIndexIndustry extends BaseDomain {
	/**
	 * 父级ID investnew_index_industry.parent_id
	 */
	private Long parentId;

	/**
	 * 行业编码 investnew_index_industry.industry_code
	 */
	private String industryCode;

	/**
	 * 行业名称 investnew_index_industry.industry_name
	 */
	private String industryName;
}