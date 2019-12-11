/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon May 27 19:52:26 CST 2019
 * Description:
 */
package com.htf.bigdata.invest.indicatormanage.model.domain;

import lombok.Data;

/**
 * InvestnewIndexIndicatorStock investnew_index_indicator_stock
 */
@Data
public class InvestnewIndexIndicatorStock extends BaseDomain {

	/**
	 * 指标ID investnew_index_indicator_stock.indicator_id
	 */
	private Long indicatorId;

	/**
	 * 股票编码 investnew_index_indicator_stock.stock_code
	 */
	private String stockCode;

	/**
	 * 股票名称 investnew_index_indicator_stock.stock_name
	 */
	private String stockName;
}