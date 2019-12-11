package com.htf.bigdata.invest.indicatormanage.model.response.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 股票信息
 * @author: panpei
 * @date: 2019/6/13
 */
@ApiModel("股票信息")
@Data
public class StockDto {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("股票代码")
    private String stockCode;

    @ApiModelProperty("股票名称")
    private String stockName;
}
