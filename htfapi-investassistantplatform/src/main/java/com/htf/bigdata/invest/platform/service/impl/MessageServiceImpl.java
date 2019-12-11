package com.htf.bigdata.invest.platform.service.impl;

import com.htf.bigdata.invest.platform.component.websocket.MessageEndpoint;
import com.htf.bigdata.invest.platform.config.enums.CompanyTypeEnum;
import com.htf.bigdata.invest.platform.dao.invest.IInvestnewMessageBroadcastDao;
import com.htf.bigdata.invest.platform.dao.invest.IInvestnewMessageDao;
import com.htf.bigdata.invest.platform.model.bo.account.AccountBO;
import com.htf.bigdata.invest.platform.model.bo.MessageBO;
import com.htf.bigdata.invest.platform.model.invest.InvestnewMessageBroadcastModel;
import com.htf.bigdata.invest.platform.model.invest.InvestnewMessageModel;
import com.htf.bigdata.invest.platform.model.invest.InvestnewUserModel;
import com.htf.bigdata.invest.platform.model.request.MessageSendRequest;
import com.htf.bigdata.invest.platform.model.request.MessageUnreadRequest;
import com.htf.bigdata.invest.platform.model.response.MessageUnreadResponse;
import com.htf.bigdata.invest.platform.service.IAccountService;
import com.htf.bigdata.invest.platform.service.IMessageService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageServiceImpl implements IMessageService {

    private final static Logger logger = LogManager.getLogger(MessageServiceImpl.class);

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IInvestnewMessageBroadcastDao investnewMessageBroadcastDao;

    @Autowired
    private IInvestnewMessageDao investnewMessageDao;

    @Autowired
    private MessageEndpoint messageEndpoint;

    @Override
    public Boolean sendBroadcast(String senderUserId, Long companyId, CompanyTypeEnum companyType, String message) {
        if (StringUtils.isEmpty(message)){
            return false;
        }
        if (StringUtils.isEmpty(senderUserId)){
            senderUserId = "0";
        }
        InvestnewMessageBroadcastModel model = new InvestnewMessageBroadcastModel();
        model.setSender_user_id(senderUserId);
        if (companyId != null || companyType != null){
            model.setCompany_id(companyId);
            model.setCompany_type(companyType.name().toLowerCase());
        }
        model.setMessage(message);
        Boolean result = investnewMessageBroadcastDao.insertSelective(model) > 0;
        if (!result){
            return false;
        }
        if (companyId == null || companyType == null){
            notifyAll(message);
        }else{
            notify(companyId,companyType,message);
        }
        return true;
    }

    @Override
    public Boolean revokeBroadcast(Long broadcastMessageId) {
        InvestnewMessageBroadcastModel model = new InvestnewMessageBroadcastModel();
        model.setId(broadcastMessageId);
        model.setIs_revoke(true);
        investnewMessageBroadcastDao.updateByPrimaryKeySelective(model);
        investnewMessageDao.setRevoke(broadcastMessageId);
        return true;
    }

    @Override
    public Boolean sendMessage(MessageSendRequest request) {
        if (StringUtils.isEmpty(request.getMessage())){
            return false;
        }
        List<String> receiverUserIds = Arrays.asList(request.getReceiver_userId().split(","));
        Integer success = 0;
        for (String item : new HashSet<>(receiverUserIds)){
            try{
                InvestnewMessageModel model = new InvestnewMessageModel();
                model.setSender_user_id(request.getUserId());
                model.setReceiver_user_id(item);
                model.setMessage(request.getMessage());
                model.setObject_id(request.getObject_id());
                model.setObject_type(request.getObject_type());
                model.setObject_pid(request.getObject_pid());
                success += investnewMessageDao.insertSelective(model);
            }catch (Exception e){
                logger.error(e);
                continue;
            }
        }
        if (success > 0){
            notify(receiverUserIds,request.getMessage());
        }
        return success > 0;
    }

    @Override
    public Boolean revokeMessage(Long messageId) {
        InvestnewMessageModel model = new InvestnewMessageModel();
        model.setId(messageId);
        model.setIs_revoke(true);
        return investnewMessageDao.updateByPrimaryKeySelective(model) > 0;
    }

    @Override
    public MessageUnreadResponse getUnreadMessage(MessageUnreadRequest request) {
        handleBroadcast(request.getUserId());//处理未读广播

        Date endTime = request.getEnd_time() != null ? new Date(request.getEnd_time()) : new Date();
        MessageUnreadResponse messageUnreadResponse = new MessageUnreadResponse();
        Integer count = investnewMessageDao.countByReceiverUserIdUnread(request.getUserId(),request.getType(),endTime,request.getOffset(),request.getLimit());
        messageUnreadResponse.setTotalCount(count);
        if (count.equals(0)){
            return messageUnreadResponse;
        }
        List<InvestnewMessageModel> modelList = investnewMessageDao.selectByReceiverUserIdUnread(request.getUserId(),request.getType(),endTime,request.getOffset(),request.getLimit());
        List<MessageBO> messageBOList = new ArrayList<>();
        for (InvestnewMessageModel item : modelList){
            MessageBO messageBO = new MessageBO();
            messageBO.setMessageId(item.getId());
            messageBO.setSenderUserId(item.getSender_user_id());
            messageBO.setReceiverUserId(item.getReceiver_user_id());
            messageBO.setIsBroadcast(item.getBroadcast_id() != null);
            messageBO.setMessage(item.getMessage());
            messageBO.setObjectId(item.getObject_id());
            messageBO.setObjectType(item.getObject_type());
            messageBO.setObjectPid(item.getObject_pid());
            messageBO.setCreateTime(item.getCreate_time());
            messageBOList.add(messageBO);
        }
        messageUnreadResponse.setList(messageBOList);
        return messageUnreadResponse;
    }

    /**
     * 获取某个时间往后的所有广播
     * @param minimumId
     * @param companyId
     * @param companyType
     * @return
     */
    private List<InvestnewMessageBroadcastModel> getAllBroadcast(Long minimumId, Long companyId, CompanyTypeEnum companyType) {
        List<InvestnewMessageBroadcastModel> investnewMessageBroadcastModelList = investnewMessageBroadcastDao.selectNoCompany(minimumId);
        if (companyId != null && companyType != null){
            investnewMessageBroadcastModelList.addAll(investnewMessageBroadcastDao.selectByCompany(minimumId,companyId,companyType.name().toLowerCase()));
        }
        Collections.sort(investnewMessageBroadcastModelList,(o1,o2)->{return o2.getCreate_time().compareTo(o1.getCreate_time());});
        return investnewMessageBroadcastModelList;
    }

    /**
     * 把所有未读广播构造为个人消息
     * @param userId
     */
    private void handleBroadcast(String userId) {
        //获取收到的最后一条广播和其时间
        InvestnewMessageModel lastBroadcast = investnewMessageDao.getLastBroadcastByReceiverUserId(userId);
        Long lastBroadcastId = lastBroadcast != null ? lastBroadcast.getBroadcast_id() : 0L;

        //获取公司归属
        InvestnewUserModel investnewUserModel = accountService.userModelByUserId(userId);
        Long companyId = investnewUserModel != null ? investnewUserModel.getCompany_id() : null;
        CompanyTypeEnum companyTypeEnum = null;
        try{
            companyTypeEnum = CompanyTypeEnum.valueOf(investnewUserModel.getCompany_type().toUpperCase());
        }catch (Exception e){};

        //获取所有广播并处理
        List<InvestnewMessageBroadcastModel> investnewMessageBroadcastModelList = getAllBroadcast(lastBroadcastId,companyId,companyTypeEnum);
        for (InvestnewMessageBroadcastModel item : investnewMessageBroadcastModelList){
            try{
                InvestnewMessageModel model = new InvestnewMessageModel();
                model.setSender_user_id(item.getSender_user_id());
                model.setReceiver_user_id(userId);
                model.setBroadcast_id(item.getId());
                model.setMessage(item.getMessage());
                model.setCreate_time(item.getCreate_time());
                investnewMessageDao.insertSelective(model);
            }catch (Exception e){
                logger.error(e);
            }
        }
    }

    @Override
    public Boolean setRead(String userId) {
        return investnewMessageDao.setReadByUserId(userId) > 0;
    }

    @Override
    public Boolean setRead(List<Long> messageIds) {
        return investnewMessageDao.setReadByMessageIds(messageIds) > 0;
    }

    @Override
    public void notifyAll(String message) {
        messageEndpoint.sendMessageToAll(message);
    }

    @Override
    public void notify(List<String> userIds, String message) {
        for (String item : userIds){
            messageEndpoint.sendMessage(item,message);
        }
    }

    @Override
    public void notify(Long companyId, CompanyTypeEnum companyType,String message) {
        if (companyId.equals(0L)){
            messageEndpoint.sendMessageToAll(message);
            return;
        }
        List<AccountBO> accountBOList = accountService.companyUserList(companyId,companyType.name().toLowerCase());
        for (AccountBO item : accountBOList){
            messageEndpoint.sendMessage(item.getUser_id(),message);
        }
    }
}
