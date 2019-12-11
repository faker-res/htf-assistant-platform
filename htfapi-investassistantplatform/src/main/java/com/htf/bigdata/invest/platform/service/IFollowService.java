package com.htf.bigdata.invest.platform.service;

import com.htf.bigdata.invest.platform.model.request.FollowAddRequest;
import com.htf.bigdata.invest.platform.model.request.FollowRemoveRequest;

/**
 * 关注
 * @author wb-wuxiao
 */
public interface IFollowService {

    /**
     * 添加关注
     * @param request
     * @return
     */
    Boolean addFollow(FollowAddRequest request);

    /**
     * 移除关注
     * @param request
     * @return
     */
    Boolean removeFollow(FollowRemoveRequest request);
}
