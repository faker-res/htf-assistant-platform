package com.htf.bigdata.invest.indicatormanage.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xuyali
 * @date 2019/6/15 9:05
 */
@Data
@ApiModel("历史数据")
public class HistoryContent {
    @ApiModelProperty("内容")
    private String content;
}
