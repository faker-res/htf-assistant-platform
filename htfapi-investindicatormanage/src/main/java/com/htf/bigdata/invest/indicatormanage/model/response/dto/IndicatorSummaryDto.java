package com.htf.bigdata.invest.indicatormanage.model.response.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description: 指标概要信息
 * @author: panpei
 * @date: 2019/6/12
 */
@Data
@ApiModel("指标概要信息")
public class IndicatorSummaryDto {

    // 指标id
    @ApiModelProperty("指标id")
    private Long indicatorId;

    // 指标名称
    @ApiModelProperty("指标名称")
    private String indicatorName;


    // 指标组下的各指标信息
    @ApiModelProperty("指标组下的各指标信息")
    List<IndicatorSummaryDto> indicatorList;
}
