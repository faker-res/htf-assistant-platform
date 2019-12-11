/**
 * 
 */
package com.htf.bigdata.invest.indicatormanage.service;

import java.util.List;

import com.htf.bigdata.invest.indicatormanage.model.business.QuotaBean;
import com.htf.bigdata.invest.indicatormanage.model.vo.AddCommentVo;
import com.htf.bigdata.invest.indicatormanage.model.vo.ConfirmDataInput;
import com.htf.bigdata.invest.indicatormanage.model.vo.IndicatorMsg;
import com.htf.bigdata.invest.indicatormanage.model.vo.IndicatorVo;
import com.htf.bigdata.invest.indicatormanage.model.vo.QueryIndicatorDataVo;

/**
 * @author zhairp createDate: 2019-06-12
 */
public interface IndicatorService {

	/**
	 * 批量查看指标数值
	 * 
	 * @author zhairp createDate: 2019-06-12
	 * @param input
	 * @return
	 */
	List<IndicatorVo> batchQueryIndicatorData(QueryIndicatorDataVo input);

	/**
	 * 查看单个指标数值
	 * 
	 * @author zhairp createDate: 2019-06-12
	 * @param indicatorId
	 * @return
	 */
	IndicatorVo singleQueryIndicatorData(Long indicatorId);

	/**
	 * 批量确认指标数值
	 * 
	 * @author zhairp createDate: 2019-06-12
	 * @param input
	 * @return
	 */
	int batchConfirmData(ConfirmDataInput input);

	/**
	 * 新增指标数值点评
	 * 
	 * @author zhairp createDate: 2019-06-13
	 * @param input
	 * @return
	 */
	int addIndicatorDataComment(AddCommentVo input);

	/**
	 * 批量导入指标
	 * 
	 * @author zhairp createDate: 2019-06-14
	 * @param task
	 * @return
	 */
	List<IndicatorMsg> batchAddIndicator(List<QuotaBean> task,String userId);

}
