/**
 * 
 */
package com.htf.bigdata.invest.indicatormanage.model.vo;

import com.htf.bigdata.invest.indicatormanage.model.domain.BaseVo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhairp createDate: 2019-06-13
 */
@Getter
@Setter
public class AddCommentVo extends BaseVo {
	private Long pObjectId;
	private Long objectId;
	/**
	 * 评论类型：1-指标数值 2-特殊指标
	 */
	private Integer type;
	private String content;
}
