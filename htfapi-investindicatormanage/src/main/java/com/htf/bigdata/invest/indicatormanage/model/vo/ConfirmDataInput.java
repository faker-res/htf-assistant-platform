/**
 * 
 */
package com.htf.bigdata.invest.indicatormanage.model.vo;

import com.htf.bigdata.invest.indicatormanage.model.domain.BaseVo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhairp createDate: 2019-06-12
 */
@Getter
@Setter
public class ConfirmDataInput extends BaseVo {
	private Long indicatorId;
	private Integer status;
}
