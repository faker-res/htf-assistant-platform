/**
 * 
 */
package com.htf.bigdata.invest.indicatormanage.model.vo;

import lombok.Data;

/**
 * @author zhairp createDate: 2019-06-14
 */
@Data
public class IndicatorMsg {
	private String indicatorName;
	private String message;

	/**
	 * @author zhairp createDate: 2019-06-14
	 * @return
	 */
	@Override
	public String toString() {
		return "IndicatorMsg [indicatorName=" + indicatorName + ", message=" + message + "]";
	}

}
