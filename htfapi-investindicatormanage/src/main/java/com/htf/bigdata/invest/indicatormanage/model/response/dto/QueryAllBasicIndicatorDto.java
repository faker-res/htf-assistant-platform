package com.htf.bigdata.invest.indicatormanage.model.response.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description: 查询所有基础指标的返回值
 * @author: panpei
 * @date: 2019/6/14
 */
@Data
@ApiModel("查询所有基础指标的返回值")
public class QueryAllBasicIndicatorDto {

    @ApiModelProperty("行业名称或我的管理")
    private String name;

    @ApiModelProperty("行业下指标信息")
    private List<QueryAllBasicIndicatorInfoDto> indicatorInfoList;
}
