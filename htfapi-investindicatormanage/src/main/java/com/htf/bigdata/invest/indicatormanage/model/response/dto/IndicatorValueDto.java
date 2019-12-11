package com.htf.bigdata.invest.indicatormanage.model.response.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xuyali
 * @date 2019/6/13 10:09
 */
@Data
@ApiModel("指标值")
public class IndicatorValueDto {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("指标id")
    private Long indicatorId;
    @ApiModelProperty(" 指标时间")
    private Date indicatorTime;
    @ApiModelProperty("指标值")
    private BigDecimal indicatorData;
    @ApiModelProperty("指标状态 待确认-0/确认-1")
    private Integer status;
    @ApiModelProperty("是否有点评")
    private Boolean isComment;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
}
