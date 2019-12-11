package com.htf.bigdata.invest.indicatormanage.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.List;

/**
 * @description: 创建指标请求参数
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
@Data
@ApiModel(value = "创建指标请求参数")
public class AddIndicatorRequest {

    // 用户id
    @NotBlank(message = "参数[userId]不能为空")
    @ApiModelProperty(name = "用户id")
    private String userId;

    // 指标名称
    @NotBlank(message = "参数[indicatorName]不能为空")
    @ApiModelProperty(name = "指标名称")
    private String indicatorName;

    // 指标单位
    @NotBlank(message = "参数[indicatorName]不能为空")
    @ApiModelProperty(name = "指标名称")
    private String indicatorUnit;

    // 更新频率
    @NotBlank(message = "参数[indicatorName]不能为空")
    @ApiModelProperty(name = "更新频率")
    private String indicatorFrequency;

    // 数据来源
    @NotBlank(message = "参数[dataSource]不能为空")
    @ApiModelProperty(name = "数据来源")
    private String dataSource;

    // 关联的行业
    @Valid
    @NotEmpty(message = "参数[industries]不能为空")
    @ApiModelProperty(name = "关联的行业")
    private List<IndustryItem> industries;

    // 关联的股票
    @Valid
    @NotEmpty(message = "参数[stocks]不能为空")
    @ApiModelProperty(name = "关联的股票")
    private List<StockItem> stocks;

    // 历史数据
    @NotEmpty(message = "参数[dataList]不能为空")
    @ApiModelProperty(name = "历史数据")
    private List<List<String>> dataList;
}
