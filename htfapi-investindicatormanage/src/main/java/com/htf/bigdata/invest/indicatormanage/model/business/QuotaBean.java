package com.htf.bigdata.invest.indicatormanage.model.business;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author zhairp createDate: 2019-06-10
 */
@Data
public class QuotaBean {
	private String name;
	private String unit;
	private String frequency;
	private String[] industries;
	private String[] stockCodes;
	private List<QuotaValue> datas = new ArrayList<QuotaValue>();

	@Override
	public String toString() {
		return "[name=" + name + ", unit=" + unit + ", frequency=" + frequency + ", industries=" + industries + ", stockCodes=" + stockCodes + ", datas=" + datas + "]";
	}

	public QuotaBean(String name) {
		super();
		this.name = name;
	}

	public QuotaBean() {
		super();
	}

}
