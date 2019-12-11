/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon May 27 19:52:26 CST 2019
 * Description:
 */
package com.htf.bigdata.invest.indicatormanage.model.domain;

import lombok.Data;

import java.util.Date;

/**
 * InvestnewIndexGroupPublish investnew_index_group_publish
 */
@Data
public class InvestnewIndexGroupPublish extends BaseDomain {

	/**
	 * 指标组ID investnew_index_group_relation.indicator_group_id
	 */
	private Long indicatorGroupId;

	/**
	 * 指标ID investnew_index_group_relation.indicator_id
	 */
	private Long indicatorId;

	/**
	 * 发布的图配置
	 * investnew_index_group_relation.chart_setting
	 */
	private String chartSetting;

	/**
	 * 发布的图开始时间
	 * investnew_index_group_relation.chart_setting
	 */
	private Date startTime;

	/**
	 * 发布的图结束时间
	 * investnew_index_group_relation.chart_setting
	 */
	private Date endTime;

}