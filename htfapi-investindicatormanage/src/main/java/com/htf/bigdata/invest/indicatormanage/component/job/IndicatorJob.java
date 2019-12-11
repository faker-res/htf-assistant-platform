package com.htf.bigdata.invest.indicatormanage.component.job;

import com.alibaba.fastjson.JSON;
import com.htf.bigdata.invest.indicatormanage.dao.invest.InvestnewIndexIndicatorMapper;
import com.htf.bigdata.invest.indicatormanage.model.domain.InvestnewIndexIndicator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description: 指标定时任务
 * @author: panpei
 * @date: 2019/6/10
 */
@Component
public class IndicatorJob {

    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private InvestnewIndexIndicatorMapper indexIndicatorMapper;

    @Autowired
    private IndicatorJobTransactionService indicatorJobTransactionService;

    private static ExecutorService FIXED_THREAD_POOL;
    {
        FIXED_THREAD_POOL = Executors.newFixedThreadPool(10);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Async
    public void BatchHandleNotUpdatedIndicator() {
        logger.info("IndicatorJob.handleNotUpdatedIndicator begin");
        try {
            // 获取手动更新数据的基础指标
            List<InvestnewIndexIndicator> indexIndicatorList = indexIndicatorMapper.selectNoAutoIndicatorList();
            if (indexIndicatorList == null || indexIndicatorList.isEmpty()) {
                logger.info("no auto indicator list is empty");
                return;
            }
            logger.info("no auto indicator list: {}", JSON.toJSONString(indexIndicatorList));

            // 循环处理每个指标
            for (InvestnewIndexIndicator indexIndicator : indexIndicatorList) {
                logger.info("handle indicator, indicator: {}", JSON.toJSONString(indexIndicator));
                FIXED_THREAD_POOL.execute(() -> {
                    try {
                        indicatorJobTransactionService.handleNotUpdatedIndicator(indexIndicator);
                    } catch (Exception e1) {
                        try {
                            indicatorJobTransactionService.handleNotUpdatedIndicator(indexIndicator);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            logger.error("handle indicator fail, indicator: {}, time: {}, error: {}", JSON.toJSONString(indexIndicator), System.currentTimeMillis(), e2);
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("IndicatorJob.handleNotUpdatedIndicator time:{}, error:{}", System.currentTimeMillis(), e);
        }
        logger.info("IndicatorJob.handleNotUpdatedIndicator end");
    }
}
