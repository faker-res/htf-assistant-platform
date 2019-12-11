/**
 * 
 */
package com.htf.bigdata.invest.indicatormanage.model.vo;

import java.util.List;

import com.htf.bigdata.invest.indicatormanage.model.domain.InvestnewIndexIndicatorData;

import lombok.Data;

/**
 * @author zhairp createDate: 2019-06-12
 */
@Data
public class IndicatorVo {
	private Long id;
	private String indicatorName;
	private String indicatorUnit;
	private String indicatorFrequency;
	private String dataSource;
	private List<InvestnewIndexIndicatorData> indicatorDatas;
}
