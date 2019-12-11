/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon May 27 19:52:26 CST 2019
 * Description:
 */
package com.htf.bigdata.invest.indicatormanage.dao.invest;

import com.htf.bigdata.invest.indicatormanage.model.domain.InvestnewIndexIndicatorData;
import com.htf.bigdata.invest.indicatormanage.model.response.dto.QueryUpdatedIndicatorDataResponse;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @description: investnew_index_indicator_data
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public interface InvestnewIndexIndicatorDataMapper {
    /**
     * deleteByPrimaryKey
     * @param Long id
     * @return int 
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert
     * @param InvestnewIndexIndicatorData record
     * @return int 
     */
    int insert(InvestnewIndexIndicatorData record);

    /**
     * insertSelective
     * @param InvestnewIndexIndicatorData record
     * @return int 
     */
    int insertSelective(InvestnewIndexIndicatorData record);

    /**
     * selectByPrimaryKey
     * @param Long id
     * @return InvestnewIndexIndicatorData 
     */
    InvestnewIndexIndicatorData selectByPrimaryKey(Long id);

    /**
     * updateByPrimaryKeySelective
     * @param InvestnewIndexIndicatorData record
     * @return int 
     */
    int updateByPrimaryKeySelective(InvestnewIndexIndicatorData record);

    /**
     * updateByPrimaryKey
     * @param InvestnewIndexIndicatorData record
     * @return int 
     */
    int updateByPrimaryKey(InvestnewIndexIndicatorData record);

    /**
     * 批量插入
     * @param indexIndicatorDataList
     * @return
     */
    int insertBatch(@Param("list") List<InvestnewIndexIndicatorData> indexIndicatorDataList);

    /**
     * 根据indicatorId、startTime和endTime获取数据
     * @param indicatorId
     * @param startTime
     * @param endTime
     * @return
     */
    InvestnewIndexIndicatorData selectByIndicatorIdAndStartTimeAndEndTime(@Param("indicatorId") Long indicatorId,
                                                                          @Param("starTime") Date startTime,
                                                                          @Param("endTime") Date endTime);

    /**
     * 根据indicatorId和status查询数据个数
     * @param indicatorId
     * @param status
     * @return
     */
    int getCountByIndicatorIdAndStatus(@Param("indicatorId") Long indicatorId, @Param("status") Integer status);

    /**
     * 查询待更新的指标数据
     * @param indicatorIdList
     * @param offset
     * @param limit
     * @return
     */
    List<QueryUpdatedIndicatorDataResponse> selectUpdatedIndicatorData(@Param("list") List<Long> indicatorIdList,
                                                                       @Param("offset") Integer offset,
                                                                       @Param("limit") Integer limit);
    List<InvestnewIndexIndicatorData> queryIndicatorDatas(Long indicatorId);

    /**
     * 查询最近一次有值的日期
     * @param indicatorId
     * @return
     */
    Date selectRecentTimeByIndicatorIdAndType(@Param("indicatorId") Long indicatorId);
	
    int batchConfirmData(@Param("indicatorId") Long indicatorId, @Param("status") Integer status);

    /**
     * 查询一段时间内的数据
     * @param indicatorId
     * @param startTime
     * @param endTime
     * @return
     */
    List<InvestnewIndexIndicatorData> selectByIndicatorIdAndTime(@Param("indicatorId") Long indicatorId,
                                                                 @Param("starTime") Date startTime,
                                                                 @Param("endTime") Date endTime,
                                                                 @Param("statusList") List<Integer> statusList);

    /**
     * 根据indicatorIdList和currentStatus更新数据状态updateStatus(currentStatus->updateStatus)
     * @param indicatorIdList
     * @param updateStatus 更新后状态
     * @param currentStatus 当前状态
     * @param editor 编辑者
     * @return
     */
    int updateStatusByIndicatorIdListAndStatus(@Param("list") List<Long> indicatorIdList,
                                               @Param("updateStatus") Integer updateStatus,
                                               @Param("currentStatus") Integer currentStatus,
                                               @Param("editor") String editor);

    List<InvestnewIndexIndicatorData> selectConfirmedIndicatorValue(@Param("indicatorId") Long indicatorId,
                                                                    @Param("startTime") Date startTime,
                                                                    @Param("endTime") Date endTime);
}