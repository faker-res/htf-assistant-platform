/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon May 27 19:52:26 CST 2019
 * Description:
 */
package com.htf.bigdata.invest.indicatormanage.model.domain;

import lombok.Data;

/**
 * InvestnewIndexIndicatorComment investnew_index_indicator_comment
 */
@Data
public class InvestnewIndexIndicatorComment extends BaseDomain {
	/**
	 * 点评父级ID:根评论或者1级评论-0
	 * investnew_index_indicator_comment.pid
	 */
	private Long pid;

	/**
	 * 评论对象ID
	 * investnew_index_indicator_comment.object_id
	 */
	private Long objectId;

	/**
	 * 父评论对象id，可为空
	 * investnew_index_indicator_comment.p_object_id
	 */
	private Long pObjectId;

	/**
	 * 评论类型：1-指标数值
	 * investnew_index_indicator_comment.type
	 */
	private Integer type;

	/**
	 * 用户ID investnew_index_indicator_comment.user_id
	 */
	private String userId;

	/**
	 * 点评内容 investnew_index_indicator_comment.content
	 */
	private String content;

}