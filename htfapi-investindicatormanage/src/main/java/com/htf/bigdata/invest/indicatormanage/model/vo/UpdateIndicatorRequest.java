package com.htf.bigdata.invest.indicatormanage.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: 修改指标信息请求参数
 * @author: panpei
 * @date: 2019/6/3
 */
@Data
@ApiModel(value = "修改指标信息请求参数")
public class UpdateIndicatorRequest {

    // 用户id
    @NotBlank(message = "参数[userId]不为空")
    @ApiModelProperty(name = "用户id")
    private String userId;

    // 指标id
    @NotNull(message = "参数[indicatorId]不为空")
    @ApiModelProperty(name = "指标id")
    private Long indicatorId;

    // 指标名称
    @ApiModelProperty(name = "指标名称")
    private String indicatorName;

    // 关联股票
    @ApiModelProperty(name = "关联股票")
    private List<StockItem> stocks;

    // 更新状态 0:正常 1:停止
    @ApiModelProperty(name = "更新状态")
    private String updateStatus;

    // 通知状态 0:正常 1:停止
    @ApiModelProperty(name = "通知状态")
    private String noticeStatus;
}
