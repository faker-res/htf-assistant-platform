package com.htf.bigdata.invest.platform.service.impl;

import com.htf.bigdata.invest.platform.dao.invest.IInvestnewFeedbackDao;
import com.htf.bigdata.invest.platform.model.bo.FeedbackBO;
import com.htf.bigdata.invest.platform.model.invest.InvestnewFeedbackModel;
import com.htf.bigdata.invest.platform.model.invest.InvestnewUserModel;
import com.htf.bigdata.invest.platform.model.response.FeedbackListResponse;
import com.htf.bigdata.invest.platform.service.IAccountService;
import com.htf.bigdata.invest.platform.service.IFeedbackService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FeedbackServiceImpl implements IFeedbackService {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IInvestnewFeedbackDao investnewFeedbackDao;

    @Override
    public Boolean add(String userId, String content, String contact_info) {
        InvestnewFeedbackModel model = new InvestnewFeedbackModel();
        model.setUser_id(userId);
        model.setContact_info(contact_info);
        model.setContent(content);
        if (StringUtils.isNotEmpty(userId)){
            InvestnewUserModel investnewUserModel = accountService.userModelByUserId(userId);
            if (investnewUserModel != null){
                model.setContacts(investnewUserModel.getNickname());
            }
        }
        return investnewFeedbackDao.insertSelective(model) > 0;
    }

    @Override
    public Boolean finish(Long feedbackId) {
        InvestnewFeedbackModel model = new InvestnewFeedbackModel();
        model.setId(feedbackId);
        model.setFinish_time(new Date());
        return investnewFeedbackDao.updateByPrimaryKeySelective(model) > 0;
    }

    @Override
    public FeedbackListResponse get(Integer offset, Integer limit) {
        FeedbackListResponse feedbackListResponse = new FeedbackListResponse();

        Integer count = investnewFeedbackDao.count();
        feedbackListResponse.setTotal(count);
        if (count.equals(0)){
            return feedbackListResponse;
        }

        List<InvestnewFeedbackModel> investnewFeedbackModelList = investnewFeedbackDao.getList(offset,limit);
        List<FeedbackBO> feedbackBOList = new ArrayList<>();
        for (InvestnewFeedbackModel item : investnewFeedbackModelList){
            FeedbackBO feedbackBO = new FeedbackBO();
            feedbackBO.setFeedbackId(item.getId());
            feedbackBO.setUserId(item.getUser_id());
            feedbackBO.setContent(item.getContent());
            feedbackBO.setContactInfo(item.getContact_info());
            feedbackBO.setContacts(item.getContacts());
            feedbackBO.setFinishTime(item.getFinish_time());
            feedbackBO.setCreateTime(item.getCreate_time());
            feedbackBOList.add(feedbackBO);
        }
        feedbackListResponse.setList(feedbackBOList);
        return feedbackListResponse;
    }
}
