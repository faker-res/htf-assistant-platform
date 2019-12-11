package com.htf.bigdata.invest.indicatormanage.model.response.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xuyali
 * @date 2019/6/13 10:27
 */
@Data
@ApiModel("指标点评Dto")
public class IndicatorCommentDto {

    @ApiModelProperty("评论对象ID")
    private Long objectId;

    @ApiModelProperty("评论类型：1-指标数值,2-特殊指标")
    private Integer type;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("点评内容")
    private String content;

    @ApiModelProperty("点评日期")
    private Date commentTime;

    @ApiModelProperty(" 指标时间")
    private Date indicatorTime;

    @ApiModelProperty("指标值")
    private BigDecimal indicatorData;
}
