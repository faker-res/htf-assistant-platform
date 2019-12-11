/**
 * 
 */
package com.htf.bigdata.invest.indicatormanage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htf.bigdata.invest.indicatormanage.component.context.HtfContext;
import com.htf.bigdata.invest.indicatormanage.dao.invest.InvestnewIndexIndicatorCommentMapper;
import com.htf.bigdata.invest.indicatormanage.dao.invest.InvestnewIndexIndicatorDataMapper;
import com.htf.bigdata.invest.indicatormanage.dao.invest.InvestnewIndexIndicatorMapper;
import com.htf.bigdata.invest.indicatormanage.model.business.QuotaBean;
import com.htf.bigdata.invest.indicatormanage.model.business.QuotaValue;
import com.htf.bigdata.invest.indicatormanage.model.domain.InvestnewIndexIndicator;
import com.htf.bigdata.invest.indicatormanage.model.domain.InvestnewIndexIndicatorComment;
import com.htf.bigdata.invest.indicatormanage.model.vo.AddCommentVo;
import com.htf.bigdata.invest.indicatormanage.model.vo.AddIndicatorRequest;
import com.htf.bigdata.invest.indicatormanage.model.vo.ConfirmDataInput;
import com.htf.bigdata.invest.indicatormanage.model.vo.IndicatorMsg;
import com.htf.bigdata.invest.indicatormanage.model.vo.IndicatorVo;
import com.htf.bigdata.invest.indicatormanage.model.vo.IndustryItem;
import com.htf.bigdata.invest.indicatormanage.model.vo.QueryIndicatorDataVo;
import com.htf.bigdata.invest.indicatormanage.model.vo.StockItem;
import com.htf.bigdata.invest.indicatormanage.service.IIndicatorManageService;
import com.htf.bigdata.invest.indicatormanage.service.IndicatorService;

/**
 * @author zhairp createDate: 2019-06-12
 */
@Service
public class IndicatorServiceImpl implements IndicatorService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private InvestnewIndexIndicatorMapper investnewIndexIndicatorMapper;

	@Autowired
	private InvestnewIndexIndicatorDataMapper investnewIndexIndicatorDataMapper;

	@Autowired
	private InvestnewIndexIndicatorCommentMapper investnewIndexIndicatorCommentMapper;

	@Autowired
	private IIndicatorManageService indicatorManageService;

	/**
	 * @author zhairp createDate: 2019-06-12
	 * @param input
	 * @return
	 */
	@Override
	public List<IndicatorVo> batchQueryIndicatorData(QueryIndicatorDataVo input) {
		List<IndicatorVo> output = new ArrayList<IndicatorVo>();
		if (!CollectionUtils.isEmpty(input.getIndicatorIds())) {
			input.getIndicatorIds().forEach(indicatorId -> {
				output.add(singleQueryIndicatorData(indicatorId));
			});
		}
		return output;
	}

	/**
	 * @author zhairp createDate: 2019-06-12
	 * @param indicatorId
	 * @return
	 */
	@Override
	public IndicatorVo singleQueryIndicatorData(Long indicatorId) {
		IndicatorVo indicator = new IndicatorVo();
		InvestnewIndexIndicator investnewIndexIndicator = investnewIndexIndicatorMapper.selectByPrimaryKey(indicatorId);
		if (null != investnewIndexIndicator) {
			indicator.setId(indicatorId);
			indicator.setIndicatorName(investnewIndexIndicator.getIndicatorName());
			indicator.setIndicatorUnit(investnewIndexIndicator.getIndicatorUnit());
		}
		indicator.setIndicatorDatas(investnewIndexIndicatorDataMapper.queryIndicatorDatas(indicatorId));
		return indicator;
	}

	/**
	 * @author zhairp createDate: 2019-06-12
	 * @param indicatorId
	 * @param status
	 * @return
	 */
	@Override
	public int batchConfirmData(ConfirmDataInput input) {
		return investnewIndexIndicatorDataMapper.batchConfirmData(input.getIndicatorId(), input.getStatus());
	}

	/**
	 * @author zhairp createDate: 2019-06-13
	 * @param input
	 * @return
	 */
	@Override
	public int addIndicatorDataComment(AddCommentVo input) {
		InvestnewIndexIndicatorComment comment = new InvestnewIndexIndicatorComment();
		comment.setUserId(HtfContext.htfContext.get().getUserId());
		comment.setPObjectId(input.getPObjectId());
		comment.setObjectId(input.getObjectId());
		comment.setType(input.getType());
		comment.setContent(input.getContent());
		return investnewIndexIndicatorCommentMapper.insertSelective(comment);
	}

	/**
	 * @author zhairp createDate: 2019-06-14
	 * @param task
	 * @return
	 */
	@Override
	public List<IndicatorMsg> batchAddIndicator(List<QuotaBean> task, String userId) {
		logger.info("userId:{}", userId);
		// 处理批量导入指标任务
		List<IndicatorMsg> result = new ArrayList<IndicatorMsg>();
		task.forEach(quotaBean -> {
			try {
				AddIndicatorRequest request = new AddIndicatorRequest();
				request.setUserId(userId);
				request.setIndicatorName(quotaBean.getName());
				request.setIndicatorUnit(quotaBean.getUnit());
				request.setIndicatorFrequency(quotaBean.getFrequency());
				request.setDataSource("手动");
				List<IndustryItem> industriesNew = new ArrayList<IndustryItem>();
				String[] industries = quotaBean.getIndustries();
				if (null != industries && industries.length >= 0) {
					for (String industry : industries) {
						IndustryItem industryItem = new IndustryItem();
						industryItem.setIndustryName(industry);
						industriesNew.add(industryItem);
					}
				}
				request.setIndustries(industriesNew);
				List<StockItem> stocksNew = new ArrayList<StockItem>();
				String[] stockCodes = quotaBean.getStockCodes();
				if (null != stockCodes && stockCodes.length > 0) {
					for (String stockCode : stockCodes) {
						StockItem stockItem = new StockItem();
						stockItem.setStockCode(stockCode);
						stocksNew.add(stockItem);
					}
				}
				request.setStocks(stocksNew);
				List<List<String>> dataList = new ArrayList<List<String>>();
				List<QuotaValue> datas = quotaBean.getDatas();
				if (!CollectionUtils.isEmpty(datas)) {
					datas.forEach(quotaValue -> {
						List<String> data = new ArrayList<String>();
						data.add(quotaValue.getTime());
						data.add(String.valueOf(quotaValue.getData()));
						dataList.add(data);
					});
				}
				request.setDataList(dataList);
				indicatorManageService.saveIndicator(request);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				IndicatorMsg indicatorMsg = new IndicatorMsg();
				indicatorMsg.setIndicatorName(quotaBean.getName());
				indicatorMsg.setMessage(e.getMessage());
				result.add(indicatorMsg);
			}
		});
		return result;
	}

}
