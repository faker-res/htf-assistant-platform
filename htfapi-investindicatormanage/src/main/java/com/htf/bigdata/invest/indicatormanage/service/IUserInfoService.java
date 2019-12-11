package com.htf.bigdata.invest.indicatormanage.service;

import com.htf.bigdata.invest.indicatormanage.model.response.client.AccountResponse;

/**
 * @description: 用户信息service接口
 * @author: panpei
 * @date: 2019/6/3
 */
public interface IUserInfoService {

    /**
     * 获取用户信息
     * @param userId
     * @return
     * @throws Exception
     */
    AccountResponse getUserInfo(String userId) throws Exception;

}
