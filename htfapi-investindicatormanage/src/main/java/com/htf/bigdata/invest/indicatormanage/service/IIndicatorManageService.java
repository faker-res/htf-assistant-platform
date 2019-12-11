package com.htf.bigdata.invest.indicatormanage.service;

import com.htf.bigdata.invest.indicatormanage.config.enums.QueryIndicatorTypeEnum;
import com.htf.bigdata.invest.indicatormanage.model.response.dto.*;
import com.htf.bigdata.invest.indicatormanage.model.vo.*;

import java.util.List;

/**
 * @description: 指标管理service接口
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public interface IIndicatorManageService {

    QueryIndicatorCountResponse queryIndicatorCount(String userId) throws Exception;

    List<IndicatorSummaryDto> queryMyManageIndicator(String userId, String keyword, QueryIndicatorTypeEnum type) throws Exception;

    List<QueryUpdatedIndicatorDataResponse> queryUpdatedIndicatorData(String userId, Integer page, Integer limit) throws Exception;

    Long saveIndicator(AddIndicatorRequest request) throws Exception;

    List<StockDto> queryStock(String keyword, Integer limit) throws Exception;

    List<IndustryDto> queryIndustry() throws Exception;

    ParseIndicatorDataResponse parseIndicatorData(String importContent) throws Exception;

    IndicatorBaseInfoDto queryIndicatorBasicInfo(String userId, Long indicatorId) throws Exception;

    void updateIndicatorBasicInfo(UpdateIndicatorRequest request) throws Exception;

    List<IndicatorChartDetailDto> queryIndicatorGroupChart(String startTime, String endTime, Long indicatorId) throws Exception;

    List<IndicatorChartDetailDto> queryIndicatorChart(String indicatorIds) throws Exception;

    Long addIndicatorGroup(AddIndicatorGroupRequest request) throws Exception;

    List<QueryAllBasicIndicatorDto> queryAllBasicIndicator(String userId, Long indicatorId) throws Exception;

    List<QueryAllBasicIndicatorInfoDto> queryBasicIndicatorByKeyword(String keyword, Long indicatorId) throws Exception;

    void addIndicatorForGroup(AddIndicatorForGroupRequest request) throws Exception;

    void deleteIndicatorForGroup(DeleteIndicatorForGroupRequest request) throws Exception;

    void updateIndicatorData(UpdateIndicatorDataRequest request) throws Exception;

    Long addIndicatorData(AddIndicatorDataRequest request) throws Exception;
}
