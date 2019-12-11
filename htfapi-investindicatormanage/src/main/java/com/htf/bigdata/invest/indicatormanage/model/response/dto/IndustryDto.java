package com.htf.bigdata.invest.indicatormanage.model.response.dto;

import com.htf.bigdata.invest.indicatormanage.model.vo.IndustryItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description: 行业列表
 * @author: panpei
 * @date: 2019/6/14
 */
@Data
@ApiModel("行业列表")
public class IndustryDto {

    @ApiModelProperty("一级行业名称")
    private String firstIndustryName;

    @ApiModelProperty("二级行业列表")
    private List<IndustryItem> secondIndustryList;
}
