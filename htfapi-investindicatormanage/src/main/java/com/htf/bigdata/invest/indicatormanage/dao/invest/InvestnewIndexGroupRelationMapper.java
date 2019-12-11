/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author:
 * Date: Mon May 27 19:52:26 CST 2019
 * Description:
 */
package com.htf.bigdata.invest.indicatormanage.dao.invest;

import com.htf.bigdata.invest.indicatormanage.model.domain.InvestnewIndexGroupRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @description: investnew_index_group_relation
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public interface InvestnewIndexGroupRelationMapper {
    /**
     * deleteByPrimaryKey
     * @param Long id
     * @return int
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert
     * @param InvestnewIndexGroupRelation record
     * @return int
     */
    int insert(InvestnewIndexGroupRelation record);

    /**
     * insertSelective
     * @param InvestnewIndexGroupRelation record
     * @return int
     */
    int insertSelective(InvestnewIndexGroupRelation record);

    /**
     * selectByPrimaryKey
     * @param Long id
     * @return InvestnewIndexGroupRelation
     */
    InvestnewIndexGroupRelation selectByPrimaryKey(Long id);

    /**
     * updateByPrimaryKeySelective
     * @param InvestnewIndexGroupRelation record
     * @return int
     */
    int updateByPrimaryKeySelective(InvestnewIndexGroupRelation record);

    /**
     * updateByPrimaryKey
     * @param  record
     * @return int
     */
    int updateByPrimaryKey(InvestnewIndexGroupRelation record);

    /**
     * updateByPrimaryKeyWithBLOBs
     * @param  record
     * @return int
     */
    int updateByPrimaryKeyWithBLOBs(InvestnewIndexGroupRelation record);

    /**
     * 根据基础指标信息查询指标组id
     * @param indicatorId
     * @return
     */
    List<Long> selectGroupIdListByIndicatorId(@Param("indicatorId") Long indicatorId);

    /**
     * 查询指标组下的未删除的指标id
     * @param indicatorGroupId
     * @return
     */
    List<Long> selectIndicatorIdByGroupId(@Param("indicatorGroupId") Long indicatorGroupId, @Param("isDelete") String isDelete);

    /**
     * 查询图表信息
     * @param indicatorGroupId
     * @param indicatorId
     * @return
     */
    InvestnewIndexGroupRelation selectByGroupIdAndIndicatorId(@Param("indicatorGroupId") Long indicatorGroupId,
                                                              @Param("indicatorId") Long indicatorId);

    /**
     * 批量插入
     * @param list
     * @return
     */
    int insertBatch(@Param("list") List<InvestnewIndexGroupRelation> list);

    /**
     * 查询指标组下的指标信息
     * @param indicatorGroupId
     * @param isDelete 是否删除
     * @param isSelect 是否已选
     * @return
     */
    List<InvestnewIndexGroupRelation> selectIndicatorByGroupId(@Param("indicatorGroupId") Long indicatorGroupId,
                                                               @Param("isDelete") String isDelete,
                                                               @Param("isSelect") String isSelect);


    /**
     * 查询指定指标组添加的基础指标中以前被删除过
     * @param indicatorGroupId
     * @param indicatorIdList
     * @param isDelete
     * @return
     */
    List<Long> selectByGroupIdAndIndicatorIdAndDeleteStatus(@Param("indicatorGroupId") Long indicatorGroupId,
                                                            @Param("list") Set<Long> indicatorIdList,
                                                            @Param("isDelete") String isDelete);

    /**
     * 更新删除状态和图表配置
     * @param indicatorGroupId
     * @param indicatorId
     * @param isDelete
     * @return
     */
    int updateDeleteStatusByGroupIdAndIndicatorId(@Param("indicatorGroupId") Long indicatorGroupId,
                                                  @Param("indicatorId") Long indicatorId,
                                                  @Param("isDelete") String isDelete);

}