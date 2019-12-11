/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon May 27 19:52:26 CST 2019
 * Description:
 */
package com.htf.bigdata.invest.indicatormanage.dao.invest;

import com.htf.bigdata.invest.indicatormanage.model.domain.InvestnewIndexIndicatorComment;
import org.apache.ibatis.annotations.Param;

/**
 * @description: investnew_index_indicator_comment
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public interface InvestnewIndexIndicatorCommentMapper {
    /**
     * deleteByPrimaryKey
     * @param Long id
     * @return int 
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert
     * @param InvestnewIndexIndicatorComment record
     * @return int 
     */
    int insert(InvestnewIndexIndicatorComment record);

    /**
     * insertSelective
     * @param InvestnewIndexIndicatorComment record
     * @return int 
     */
    int insertSelective(InvestnewIndexIndicatorComment record);

    /**
     * selectByPrimaryKey
     * @param Long id
     * @return InvestnewIndexIndicatorComment 
     */
    InvestnewIndexIndicatorComment selectByPrimaryKey(Long id);

    /**
     * updateByPrimaryKeySelective
     * @param InvestnewIndexIndicatorComment record
     * @return int 
     */
    int updateByPrimaryKeySelective(InvestnewIndexIndicatorComment record);

    /**
     * updateByPrimaryKeyWithBLOBs
     * @param InvestnewIndexIndicatorComment record
     * @return int
     */
    int updateByPrimaryKeyWithBLOBs(InvestnewIndexIndicatorComment record);

    /**
     * updateByPrimaryKey
     * @param InvestnewIndexIndicatorComment record
     * @return int 
     */
    int updateByPrimaryKey(InvestnewIndexIndicatorComment record);

    /**
     * 查询评论对象的评论数
     * @param objectId
     * @param type
     * @param pid
     * @return
     */
    int selectCountByObjectIdAndTypeAndPid(@Param("objectId") Long objectId,
                                           @Param("type") Integer type,
                                           @Param("pid") Integer pid);

    /**
     * 查询普通指标数值最新评论
     * @param indicatorId
     * @return
     */
    InvestnewIndexIndicatorComment selectLastComment(@Param("indicatorId") Long indicatorId);

    /**
     * 查询特殊指标评论
     * @param indicatorId
     * @return
     */
    InvestnewIndexIndicatorComment selectSpecialIndicatorComment(@Param("indicatorId") Long indicatorId);
}