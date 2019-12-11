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
 * InvestnewIndexIndicator investnew_index_indicator
 */
@Data
public class InvestnewIndexIndicator extends BaseDomain {
	/**
	 * 指标名称 investnew_index_indicator.indicator_name
	 */
	private String indicatorName;

	/**
	 * 指标单位 investnew_index_indicator.indicator_unit
	 */
	private String indicatorUnit;

	/**
	 * 指标频率 investnew_index_indicator.indicator_frequency
	 */
	private String indicatorFrequency;

	/**
	 * 数据来源:wind/非wind investnew_index_indicator.data_source
	 */
	private String dataSource;

	/**
	 * 是否自动：1-自动；2-手动 investnew_index_indicator.is_auto
	 */
	private Integer isAuto;

	/**
	 * 指标类型:基础指标-1,指标组-2 investnew_index_indicator.indicator_type
	 */
	private Integer indicatorType;

	/**
	 * wind指标编码 investnew_index_indicator.wind_indicator_code
	 */
	private String windIndicatorCode;

	/**
	 * wind指标名称 investnew_index_indicator.wind_indicator_name
	 */
	private String windIndicatorName;

	/**
	 * 更新状态（0：正常 1：停止）
	 * investnew_index_indicator.update_status
	 */
	private String updateStatus;

	/**
	 * 通知状态（0：正常 1：停止）
	 * investnew_index_indicator.notice_status
	 */
	private String noticeStatus;

	/**
	 * 发布的数据图开始时间(格式：yyyy-MM-dd)
	 * investnew_index_indicator.start_time
	 */
	private Date startTime;

	/**
	 * 发布的数据图结束时间(格式：yyyy-MM-dd)
	 * investnew_index_indicator.end_time
	 */
	private Date endTime;

	/**
	 * 最近一次数据入库时间/发布通知更新数值时间
	 * investnew_index_indicator.push_time
	 */
	private Date pushTime;

	/**
	 * 最近一次点评时间
	 * investnew_index_indicator.comment_time
	 */
	private Date commentTime;

	/**
	 * 筛选指标看板-0-未确认/待更新，1-已确认/已更新
	 * investnew_index_indicator.query_flag
	 */
	private String queryFlag;
}