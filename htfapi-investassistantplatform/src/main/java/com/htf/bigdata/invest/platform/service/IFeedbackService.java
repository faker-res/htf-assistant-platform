package com.htf.bigdata.invest.platform.service;

import com.htf.bigdata.invest.platform.model.bo.FeedbackBO;
import com.htf.bigdata.invest.platform.model.response.FeedbackListResponse;

import java.util.List;

/**
 * 用户反馈
 * @author wb-wuxiao
 */
public interface IFeedbackService {

    /**
     * 新增反馈
     * @param userId
     * @param content
     * @param contact_info
     * @return
     */
    Boolean add(String userId,String content,String contact_info);

    /**
     * 解决反馈
     * @param feedbackId
     * @return
     */
    Boolean finish(Long feedbackId);

    /**
     * 获取反馈列表
     * @param offset
     * @param limit
     * @return
     */
    FeedbackListResponse get(Integer offset, Integer limit);
}
