/**
 * 
 */
package com.htf.bigdata.invest.indicatormanage.model.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhairp createDate: 2019-06-12
 */
@Getter
@Setter
public abstract class BaseVo {
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * token
	 */
	private String token;
}
