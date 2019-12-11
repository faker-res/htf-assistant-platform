package com.htf.bigdata.invest.indicatormanage.model.response.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: 创建组合指标列表的信息
 * @author: panpei
 * @date: 2019/6/14
 */
@Getter
@Setter
@ApiModel("创建组合指标列表的信息")
public class QueryAllBasicIndicatorInfoDto extends IndicatorBaseInfoDto {

    @ApiModelProperty("是否被选中")
    private Boolean selected = false;

    @ApiModelProperty("一级行业")
    private String firstIndustryName;

}
