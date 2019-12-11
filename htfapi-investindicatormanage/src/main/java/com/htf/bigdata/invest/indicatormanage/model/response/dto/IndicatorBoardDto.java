package com.htf.bigdata.invest.indicatormanage.model.response.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.List;

/**
 * @author xuyali
 * @date 2019/6/13 9:46
 */
@Data
@ApiModel("指标卡片信息")
public class IndicatorBoardDto {
    @ApiModelProperty("指标基础信息")
    private IndicatorBaseInfoDto baseInfoDto;
    @ApiModelProperty("发布的指标列表，包含指标数值和图形")
    private List<PublishIndicatorDto> publishIndicatorList;//针对指标组的看板
    @ApiModelProperty("特殊指标中富文本内容")
    private String content;//针对特殊指标的看板
    @ApiModelProperty("最新点评")
    private IndicatorCommentDto lastComment;

}
