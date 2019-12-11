package com.htf.bigdata.invest.indicatormanage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htf.bigdata.invest.indicatormanage.component.client.IAssistantPlatformClient;
import com.htf.bigdata.invest.indicatormanage.component.exception.CustomException;
import com.htf.bigdata.invest.indicatormanage.config.code.AssistantPlatformCodeConfig;
import com.htf.bigdata.invest.indicatormanage.config.code.SystemEnumCodeConfig;
import com.htf.bigdata.invest.indicatormanage.model.response.Response;
import com.htf.bigdata.invest.indicatormanage.model.response.client.AccountResponse;
import com.htf.bigdata.invest.indicatormanage.service.IUserInfoService;

/**
 * @description: 用户信息service实现
 * @author: panpei
 * @date: 2019/6/3
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService {

    @Autowired
    private IAssistantPlatformClient assistantPlatformClient;

    @Override
    public AccountResponse getUserInfo(String userId) throws Exception {
        Response<AccountResponse> userInfoResponse = assistantPlatformClient.getUserInfo(userId);
        if (userInfoResponse == null || userInfoResponse.getCode() != SystemEnumCodeConfig.SUCCESS.getCode() || userInfoResponse.getData() == null) {
            throw new CustomException(AssistantPlatformCodeConfig.USER_INFO_FAIL);
        }
        return userInfoResponse.getData();
    }
}
