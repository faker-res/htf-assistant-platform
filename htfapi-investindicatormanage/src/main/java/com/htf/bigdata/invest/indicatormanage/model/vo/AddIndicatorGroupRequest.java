package com.htf.bigdata.invest.indicatormanage.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * @description: 创建指标组请求参数
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
@Data
@ApiModel(value = "创建指标组请求参数")
public class AddIndicatorGroupRequest {

    // 用户id
    @NotBlank(message = "参数[userId]不能为空")
    @ApiModelProperty(name = "用户id")
    private String userId;

    // 指标组名称
    @NotBlank(message = "参数[indicatorName]不能为空")
    @ApiModelProperty(name = "指标组名称")
    private String indicatorName;

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

    // 关联的基础指标
    @NotEmpty(message = "参数[indicatorIdList]不能为空")
    @ApiModelProperty(name = "关联的基础指标")
    private Set<Long> indicatorIdList;
}
