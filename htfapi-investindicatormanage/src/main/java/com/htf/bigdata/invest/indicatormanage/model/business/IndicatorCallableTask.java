/**
 * 
 */
package com.htf.bigdata.invest.indicatormanage.model.business;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.htf.bigdata.invest.indicatormanage.model.vo.IndicatorMsg;
import com.htf.bigdata.invest.indicatormanage.service.IndicatorService;

import lombok.Data;

/**
 * @author zhairp createDate: 2019-06-14
 */
@Data
public class IndicatorCallableTask implements Callable<List<IndicatorMsg>> {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private IndicatorService indicatorService;
	private List<QuotaBean> task;
	private String userId;

	/**
	 * @param indicatorService
	 * @param task
	 */
	public IndicatorCallableTask(IndicatorService indicatorService, List<QuotaBean> task, String userId) {
		super();
		this.indicatorService = indicatorService;
		this.task = task;
		this.userId = userId;
	}

	/**
	 * @author zhairp createDate: 2019-06-17
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<IndicatorMsg> call() throws Exception {
		// 处理批量导入指标任务
		long startTime = System.currentTimeMillis();
		Thread currentThread = Thread.currentThread();
		logger.info("currentThread Id:{},Name:{},userId:{}", currentThread.getId(), currentThread.getName(), userId);
		List<IndicatorMsg> result = new ArrayList<IndicatorMsg>();
		if (!CollectionUtils.isEmpty(task)) {
			result = indicatorService.batchAddIndicator(task, userId);
			logger.info("result:{}", result);
		}
		logger.info("处理批量导入指标任务共耗时:[{}]毫秒", (System.currentTimeMillis() - startTime));
		return result;
	}

}
