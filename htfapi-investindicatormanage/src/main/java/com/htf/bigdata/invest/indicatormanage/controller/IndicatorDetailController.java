package com.htf.bigdata.invest.indicatormanage.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.htf.bigdata.invest.indicatormanage.component.context.HtfContext;
import com.htf.bigdata.invest.indicatormanage.component.util.POIUtil;
import com.htf.bigdata.invest.indicatormanage.component.util.ThreadContext;
import com.htf.bigdata.invest.indicatormanage.config.code.IndicatorManageCodeConfig;
import com.htf.bigdata.invest.indicatormanage.model.business.IndicatorCallableTask;
import com.htf.bigdata.invest.indicatormanage.model.business.QuotaBean;
import com.htf.bigdata.invest.indicatormanage.model.response.Response;
import com.htf.bigdata.invest.indicatormanage.model.vo.AddCommentVo;
import com.htf.bigdata.invest.indicatormanage.model.vo.ConfirmDataInput;
import com.htf.bigdata.invest.indicatormanage.model.vo.IndicatorMsg;
import com.htf.bigdata.invest.indicatormanage.model.vo.QueryIndicatorDataVo;
import com.htf.bigdata.invest.indicatormanage.service.IndicatorService;

/**
 * 指标详情
 * 
 * @author zhairp createDate: 2019-06-12
 */
@RestController
@RequestMapping("/detail")
public class IndicatorDetailController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IndicatorService indicatorService;

	/**
	 * 批量查看指标数值
	 * 
	 * @author zhairp createDate: 2019-06-12
	 * @param input
	 * @return
	 */
	@PostMapping("/batch-query-indicator-data")
	public Response batchQueryIndicatorData(@RequestBody QueryIndicatorDataVo input) {
		logger.info("input:{}", JSON.toJSON(input));
		return new Response(indicatorService.batchQueryIndicatorData(input));
	}

	/**
	 * 查看单个指标数值
	 * 
	 * @author zhairp createDate: 2019-06-12
	 * @param input
	 * @return
	 */
	@GetMapping("/single-query-indicator-data")
	public Response singleQueryIndicatorData(Long indicatorId) {
		logger.info("indicatorId:{}", indicatorId);
		return new Response(indicatorService.singleQueryIndicatorData(indicatorId));
	}

	/**
	 * 批量确认指标数值
	 * 
	 * @author zhairp createDate: 2019-06-12
	 * @param indicatorId
	 * @param status
	 * @return
	 */
	@PostMapping("/batch-confirm-indicator-data")
	public Response batchConfirmData(@RequestBody ConfirmDataInput input) {
		return new Response(indicatorService.batchConfirmData(input));
	}

	/**
	 * 新增指标数值点评
	 * 
	 * @author zhairp createDate: 2019-06-13
	 * @param input
	 * @return
	 */
	@PostMapping("/add-indicator-dadanta-comment")
	public Response addIndicatorDataComment(@RequestBody AddCommentVo input) {
		return new Response(indicatorService.addIndicatorDataComment(input));
	}

	/**
	 * 批量导入指标
	 * 
	 * @author zhairp createDate: 2019-06-14
	 * @param file
	 * @return
	 */
	@PostMapping("/batch-add-indicator")
	public Response batchAddIndicator(@RequestParam(required = true) MultipartFile file) {
		List<IndicatorMsg> msg = new ArrayList<IndicatorMsg>();
		try {
			List<QuotaBean> task = POIUtil.resolve(file.getInputStream());
			Future<List<IndicatorMsg>> result = ThreadContext.getThreadPool().submit(new IndicatorCallableTask(indicatorService, task, HtfContext.htfContext.get().getUserId()));
			msg = result.get();
			logger.info("======msg:{}====", msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (CollectionUtils.isEmpty(msg)) {
			return new Response("导入成功");
		}else {
			return new Response(IndicatorManageCodeConfig.ERROR_INDICATOR_BATCH_IMPORT_ERROR.getCode(),msg.toString());
		}
	}

}
