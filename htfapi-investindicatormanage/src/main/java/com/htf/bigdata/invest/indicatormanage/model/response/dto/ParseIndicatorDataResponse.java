package com.htf.bigdata.invest.indicatormanage.model.response.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description: 解析上传的历史数据返回值
 * @author: panpei
 * @date: 2019/6/12
 */
@Data
@ApiModel("解析上传的历史数据返回值")
public class ParseIndicatorDataResponse {

    // 成功解析出的
    @ApiModelProperty("成功解析出的")
    private List<List<String>> success;

    // 失败的
    @ApiModelProperty("失败的")
    private List<List<String>> fail;

}
