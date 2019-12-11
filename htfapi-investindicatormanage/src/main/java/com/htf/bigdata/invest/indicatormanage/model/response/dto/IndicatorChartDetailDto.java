package com.htf.bigdata.invest.indicatormanage.model.response.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description: 指标图信息
 * @author: panpei
 * @date: 2019/6/17
 */
@Data
@ApiModel("指标图信息")
public class IndicatorChartDetailDto {

    @ApiModelProperty("指标基础信息")
    private IndicatorBaseInfoDto baseInfoDto;

    @ApiModelProperty("指标值列表")
    private List<IndicatorValueDto> indicatorValueList;
}
