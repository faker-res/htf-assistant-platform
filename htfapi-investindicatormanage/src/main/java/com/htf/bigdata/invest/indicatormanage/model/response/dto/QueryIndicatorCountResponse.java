package com.htf.bigdata.invest.indicatormanage.model.response.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 查询待确认和待更新指标数量返回值
 * @author: panpei
 * @date: 2019/6/12
 */
@Data
@ApiModel("查询待确认和待更新指标数量返回值")
public class QueryIndicatorCountResponse {

    // 待确认的指标数量
    @ApiModelProperty("待确认的指标数量")
    private Integer unconfirmedIndicatorCount;

    // 待更新的指标数量
    @ApiModelProperty("待更新的指标数量")
    private Integer notUpdatedIndicatorCount;
}
