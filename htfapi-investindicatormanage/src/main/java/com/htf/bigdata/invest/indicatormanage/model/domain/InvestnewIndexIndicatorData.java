/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon May 27 19:52:26 CST 2019
 * Description:
 */
package com.htf.bigdata.invest.indicatormanage.model.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * InvestnewIndexIndicatorData investnew_index_indicator_data
 */
@Data
public class InvestnewIndexIndicatorData extends BaseDomain {
	/**
	 * 指标ID investnew_index_indicator_data.indicator_id
	 */
	private Long indicatorId;

	/**
	 * 指标时间:若按月更新,也存储2019-01-01 investnew_index_indicator_data.indicator_time
	 */
	private Date indicatorTime;

	/**
	 * 指标数据 investnew_index_indicator_data.indicator_data
	 */
	private BigDecimal indicatorData;

	/**
	 * 指标数据状态:待确认-0/确认-1 investnew_index_indicator_data.status
	 */
	private Integer status;

	/**
	 * 是否自动：1-自动；2-手动 investnew_index_indicator_data.is_auto
	 */
	private Integer isAuto;

}