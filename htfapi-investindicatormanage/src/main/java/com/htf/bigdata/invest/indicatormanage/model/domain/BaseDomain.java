/**
 * 
 */
package com.htf.bigdata.invest.indicatormanage.model.domain;

import java.util.Date;

import lombok.Data;

/**
 * @author zhairp createDate: 2019-05-27
 */
@Data
public abstract class BaseDomain {
	private Long id;

	private String creator;

	private String editor;

	private Date createTime;

	private Date updateTime;
}
