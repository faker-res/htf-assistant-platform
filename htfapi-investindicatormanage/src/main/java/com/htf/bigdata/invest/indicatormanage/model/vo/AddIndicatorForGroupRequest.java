package com.htf.bigdata.invest.indicatormanage.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @description: 指标组添加指标参数
 * @author: panpei
 * @date: 2019/6/14
 */
@Data
@ApiModel("指标组添加指标参数")
public class AddIndicatorForGroupRequest {

    // 用户id
    @NotBlank(message = "参数[userId]不能为空")
    @ApiModelProperty(name = "用户id")
    private String userId;

    // 指标组id
    @NotNull(message = "参数[indicatorGroupId]不能为空")
    @ApiModelProperty(name = "指标组id")
    private Long indicatorGroupId;

    // 关联的基础指标
    @NotEmpty(message = "参数[indicatorIdList]不能为空")
    @ApiModelProperty(name = "关联的基础指标")
    private Set<Long> indicatorIdList;
}
