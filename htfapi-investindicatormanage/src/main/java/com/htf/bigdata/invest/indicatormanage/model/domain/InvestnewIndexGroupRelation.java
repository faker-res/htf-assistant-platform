/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon May 27 19:52:26 CST 2019
 * Description:
 */
package com.htf.bigdata.invest.indicatormanage.model.domain;

import lombok.Data;

/**
 * InvestnewIndexGroupRelation investnew_index_group_relation
 */
@Data
public class InvestnewIndexGroupRelation extends BaseDomain {

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
	 * 是否已选
	 * investnew_index_group_relation.chart_setting
	 */
	private String isSelect;

	/**
	 * 是否删除，0否1是 默认0
	 * investnew_index_group_relation.is_delete
	 */
	private String isDelete;
}