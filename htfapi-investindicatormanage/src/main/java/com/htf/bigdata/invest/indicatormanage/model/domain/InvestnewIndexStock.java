/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon May 27 19:52:26 CST 2019
 * Description:
 */
package com.htf.bigdata.invest.indicatormanage.model.domain;

import lombok.Data;

/**
 * InvestnewIndexStock investnew_index_stock
 */
@Data
public class InvestnewIndexStock extends BaseDomain {

	/**
	 * 股票编码 investnew_index_stock.stock_code
	 */
	private String stockCode;

	/**
	 * 股票名称 investnew_index_stock.stock_name
	 */
	private String stockName;

	/**
	 * 股票市场 investnew_index_stock.market
	 */
	private String market;
}