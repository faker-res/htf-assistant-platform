/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon May 27 19:52:26 CST 2019
 * Description:
 */
package com.htf.bigdata.invest.indicatormanage.dao.invest;

import com.htf.bigdata.invest.indicatormanage.model.domain.InvestnewIndexIndicator;
import com.htf.bigdata.invest.indicatormanage.model.response.dto.IndicatorSummaryDto;
import com.htf.bigdata.invest.indicatormanage.model.response.dto.QueryAllBasicIndicatorInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @description: investnew_index_indicator
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public interface InvestnewIndexIndicatorMapper {
    /**
     * deleteByPrimaryKey
     * @param Long id
     * @return int 
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert
     * @param InvestnewIndexIndicator record
     * @return int 
     */
    int insert(InvestnewIndexIndicator record);

    /**
     * insertSelective
     * @param InvestnewIndexIndicator record
     * @return int 
     */
    int insertSelective(InvestnewIndexIndicator record);

    /**
     * selectByPrimaryKey
     * @param Long id
     * @return InvestnewIndexIndicator 
     */
    InvestnewIndexIndicator selectByPrimaryKey(Long id);

    /**
     * updateByPrimaryKeySelective
     * @param InvestnewIndexIndicator record
     * @return int 
     */
    int updateByPrimaryKeySelective(InvestnewIndexIndicator record);

    /**
     * updateByPrimaryKey
     * @param InvestnewIndexIndicator record
     * @return int 
     */
    int updateByPrimaryKey(InvestnewIndexIndicator record);

    /**
     * 查询我所管理的指标以数据更新时间和最近评论时间倒序
     * @param analystId
     * @param keyword
     * @return
     */
    List<IndicatorSummaryDto> selectMyManageIndicator(@Param("analystId") String analystId,
                                                      @Param("keyword") String keyword,
                                                      @Param("indicatorType") Integer indicatorType,
                                                      @Param("isAuto") Integer isAuto,
                                                      @Param("queryFlag") String queryFlag);

    /**
     * 获取手动更新的指标列表
     * @return
     */
    List<InvestnewIndexIndicator> selectNoAutoIndicatorList();

    /**
     * 根据analystId、isAuto、queryFlag、indicatorType查询指标id
     * @param analystId
     * @param isAuto
     * @param queryFlag
     * @param indicatorType
     * @return
     */
    List<Long> selectIdListByAutoAndQueryFlag(@Param("analystId") String analystId,
                                              @Param("isAuto") Integer isAuto,
                                              @Param("queryFlag") String queryFlag,
                                              @Param("indicatorType") Integer indicatorType);

    /**
     * 根据IdList和类型查询d
     * @param indicatorIdList
     * @param indicatorType
     * @return
     */
    Long selectIndicatorIdByIdListAndType(@Param("list") List<Long> indicatorIdList,
                                          @Param("indicatorType") Integer indicatorType);

    /**
     * 根据id集合查询概要信息
     * @param indicatorIdList
     * @return
     */
    List<IndicatorSummaryDto> selectIndicatorSummaryByIdList(@Param("list") List<Long> indicatorIdList);

    /**
     * 根据id和类型查询指标数量
     * @param indicatorIdList
     * @param indicatorType
     * @return
     */
    int selectCountByIdListAndType(@Param("list") Set<Long> indicatorIdList, @Param("indicatorType") Integer indicatorType);

    /**
     * 查询我管理的基础指标
     * @param analystId
     * @param indicatorType
     * @return
     */
    List<QueryAllBasicIndicatorInfoDto> selectByAnalystIdAndType(@Param("analystId") String analystId,
                                                                 @Param("indicatorType") Integer indicatorType);

    /**
     * 查询所有一级行业下的基础指标
     * @param indicatorType
     * @return
     */
    List<QueryAllBasicIndicatorInfoDto> selectAllIndustryIndicatorByType(Integer indicatorType);

    /**
     * 根据keyword查询基础指标
     * @param indicatorType
     * @param keyword
     * @return
     */
    List<QueryAllBasicIndicatorInfoDto> selectByIndicatorTypeAndKeyword(@Param("indicatorType") Integer indicatorType,
                                                                        @Param("keyword") String keyword);
}