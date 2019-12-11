package com.htf.bigdata.invest.platform.service;

import com.htf.bigdata.invest.platform.config.enums.CompanyTypeEnum;
import com.htf.bigdata.invest.platform.model.request.MessageSendRequest;
import com.htf.bigdata.invest.platform.model.request.MessageUnreadRequest;
import com.htf.bigdata.invest.platform.model.response.MessageUnreadResponse;

import java.util.Date;
import java.util.List;

/**
 * 消息服务
 * @author wuxiao
 */
public interface IMessageService {

    /**
     * 发送广播
     * @param senderUserId
     * @param companyId
     * @param companyType
     * @param message
     */
    Boolean sendBroadcast(String senderUserId, Long companyId, CompanyTypeEnum companyType, String message);

    /**
     * 撤回广播
     * @param broadcastMessageId
     */
    Boolean revokeBroadcast(Long broadcastMessageId);

    /**
     * 发送定点消息
     */
    Boolean sendMessage(MessageSendRequest request);

    /**
     * 撤回定点消息
     * @param messageId
     */
    Boolean revokeMessage(Long messageId);

    /**
     * 获取未读消息
     * @return
     */
    MessageUnreadResponse getUnreadMessage(MessageUnreadRequest request);

    /**
     * 设置消息已读
     * @return
     */
    Boolean setRead(String userId);
    Boolean setRead(List<Long> messageIds);

    /**
     * 发送消息通知
     */
    void notifyAll(String message);
    void notify(List<String> userIds, String message);
    void notify(Long companyId, CompanyTypeEnum companyType, String message);
}
