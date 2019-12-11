package com.htf.bigdata.invest.indicatormanage.model.business;

import java.math.BigDecimal;

import lombok.Data;

/**
 * @author zhairp createDate: 2019-06-10
 */
@Data
public class QuotaValue {
	private String time;
	private BigDecimal data;

	@Override
	public String toString() {
		return "[time=" + time + ", data=" + data + "]";
	}

}
