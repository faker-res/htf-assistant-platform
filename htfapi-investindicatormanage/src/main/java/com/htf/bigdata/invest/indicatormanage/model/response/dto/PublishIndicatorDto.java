package com.htf.bigdata.invest.indicatormanage.model.response.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author xuyali
 * @date 2019/6/15 17:06
 */
@Data
@ApiModel("指标发布信息")
public class PublishIndicatorDto {
    @ApiModelProperty("指标id")
    private Long indicatorId;
    @ApiModelProperty(" 指标名称")
    private String indicatorName;
    @ApiModelProperty("发布的图配置")
    private String chartSetting;
    @ApiModelProperty("指标值列表")
    private List<IndicatorValueDto> indicatorValueDtoList;
}
