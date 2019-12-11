package com.htf.bigdata.invest.indicatormanage.dao.invest;

import com.htf.bigdata.invest.indicatormanage.model.response.dto.IndicatorBaseInfoDto;
import com.htf.bigdata.invest.indicatormanage.model.response.dto.IndicatorSummaryDto;
import com.htf.bigdata.invest.indicatormanage.model.response.dto.IndicatorValueDto;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 指标看板单独创建一个mapper
 *
 * @author xuyali
 * @date 2019/6/13 16:24
 */
public interface IndicatorBoardMapper {
    /**
     * 查询我所管理的指标以数据更新时间和最近评论时间倒序
     *
     * @param userId
     * @return
     */
    List<IndicatorSummaryDto> selectManageIndicator(@Param("userId") String userId,
                                                    @Param("orderByUpdate") Boolean orderByUpdate,
                                                    @Param("start") int start,
                                                    @Param("pageSize") int paseSize);

    /**
     * 查询我所关注的指标以数据更新时间和最近评论时间倒序
     *
     * @param userId
     * @return
     */
    List<IndicatorSummaryDto> selectAttentionIndicator(@Param("userId") String userId,
                                                       @Param("orderByUpdate") Boolean orderByUpdate,
                                                       @Param("start") int start,
                                                       @Param("pageSize") int paseSize);

    /**
     * 查询全部指标以数据更新时间和最近评论时间倒序
     *
     * @return
     */
    List<IndicatorSummaryDto> selectAllIndicator(@Param("orderByUpdate") Boolean orderByUpdate,
                                                 @Param("start") int start,
                                                 @Param("pageSize") int paseSize);

    /**
     * 查询某行业下的指标列表并以更新时间和最近评论时间倒序
     *
     * @return
     */
    List<IndicatorSummaryDto> selectIndustryIndicator(@Param("secondIndustryName") String secondIndustryName,
                                                      @Param("orderByUpdate") Boolean orderByUpdate,
                                                      @Param("start") int start,
                                                      @Param("pageSize") int paseSize);

    /**
     * 查询搜索关键词的指标列表并以更新时间和最近评论时间倒序
     *
     * @return
     */
    List<IndicatorSummaryDto> selectKeyWordIndicator(@Param("keyWord") String keyWord,
                                                     @Param("orderByUpdate") Boolean orderByUpdate,
                                                     @Param("start") int start,
                                                     @Param("pageSize") int paseSize);

    /**
     * 根据指标id查询看板中指标数值列表
     *
     * @return
     */
    List<IndicatorValueDto> selectIndicatorValue(@Param("indicatorId") Long indicatorId,
                                                 @Param("starTime") Date startTime,
                                                 @Param("endTime") Date endTime);

    /**
     * 根据关键词或一级行业查询已关注指标ID列表
     *
     * @return
     */
    List<Long> selectAttentionIds(@Param("userId") String userId,
                                  @Param("keyWord") String keyWord,
                                  @Param("firstIndustryName") String firstIndustryName);


    /**
     * 根据关键词或一级行业查询所有指标ID列表
     *
     * @return
     */
    List<Long> selectAllIndicatorIds(@Param("keyWord") String keyWord,
                                     @Param("firstIndustryName") String firstIndustryName,
                                     @Param("start") int start,
                                     @Param("pageSize") int paseSize);


}
