package com.htf.bigdata.invest.indicatormanage.service;

import com.htf.bigdata.invest.indicatormanage.model.response.dto.IndicatorBaseInfoDto;
import com.htf.bigdata.invest.indicatormanage.model.response.dto.IndicatorBoardDto;
import com.htf.bigdata.invest.indicatormanage.model.response.dto.IndicatorValueDto;
import com.htf.bigdata.invest.indicatormanage.model.vo.BoardSearchRequest;
import com.htf.bigdata.invest.indicatormanage.model.vo.PublishIndicatorRequest;

import java.util.Date;
import java.util.List;

public interface IndicatorBoardService {

    long queryTotalCount(BoardSearchRequest searchRequest);

    List<IndicatorBoardDto> queryIndicatorBoardList(BoardSearchRequest searchRequest);

    IndicatorBaseInfoDto queryBaseinfoById(Long indicatorId, boolean queryStock);

    List<IndicatorValueDto> queryIndicatorValue(Long indicatorId, Date startTime, Date endTime);

    boolean publishIndicator(PublishIndicatorRequest publishRequest);

    boolean saveDraft(PublishIndicatorRequest publishRequest);

    List<Long> attentionIndicatorIdList(String keyWord, String firstIndustryName);

    long queryTotalCount(String keyWord, String firstIndustryName);

    List<IndicatorBaseInfoDto> allIndicatorList(String keyWord, String firstIndustryName, int pageSize, int currentPage);
}
