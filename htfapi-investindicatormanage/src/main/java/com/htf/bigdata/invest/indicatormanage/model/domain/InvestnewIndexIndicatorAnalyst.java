/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon May 27 19:52:26 CST 2019
 * Description:
 */
package com.htf.bigdata.invest.indicatormanage.model.domain;

import lombok.Data;

/**
 * InvestnewIndexIndicatorAnalyst investnew_index_indicator_analyst
 */
@Data
public class InvestnewIndexIndicatorAnalyst extends BaseDomain {

	/**
	 * 指标ID investnew_index_indicator_analyst.indicator_id
	 */
	private Long indicatorId;

	/**
	 * 研究员ID investnew_index_indicator_analyst.analyst_id
	 */
	private String analystId;

	/**
	 * 研究员名称 investnew_index_indicator_analyst.analyst_name
	 */
	private String analystName;

}