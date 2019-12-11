/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon May 27 19:52:26 CST 2019
 * Description:
 */
package com.htf.bigdata.invest.indicatormanage.model.domain;

import lombok.Data;

/**
 * InvestnewIndexIndicatorFollow investnew_index_indicator_follow
 */
@Data
public class InvestnewIndexIndicatorFollow extends BaseDomain {
	/**
	 * 关注对象
	 * investnew_index_indicator_follow.object_id
	 */
	private Long objectId;

	/**
	 * 关注类型：1-指标；2-看板
	 * investnew_index_indicator_follow.type
	 */
	private Integer type;

	/**
	 * 用户ID investnew_index_indicator_follow.user_id
	 */
	private String userId;

}