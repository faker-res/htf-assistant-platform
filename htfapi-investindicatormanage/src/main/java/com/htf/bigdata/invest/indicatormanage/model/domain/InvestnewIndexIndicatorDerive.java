/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Thu May 30 20:07:18 CST 2019
 * Description:
 */
package com.htf.bigdata.invest.indicatormanage.model.domain;

import lombok.Data;

/**
 * InvestnewIndexIndicatorDerive investnew_index_indicator_derive
 */
@Data
public class InvestnewIndexIndicatorDerive extends BaseDomain {
	/**
	 * 主键id investnew_index_indicator_derive.id
	 */
	private Long id;

	/**
	 * 指标ID investnew_index_indicator_derive.indicator_id
	 */
	private Long indicatorId;

	/**
	 * 衍生指标类型:同比-1 环比-2
	 * investnew_index_indicator_derive.derive_indicator_type
	 */
	private Integer deriveIndicatorType;

	/**
	 * 衍生指标ID investnew_index_indicator_derive.derive_indicator_id
	 */
	private Long deriveIndicatorId;

}