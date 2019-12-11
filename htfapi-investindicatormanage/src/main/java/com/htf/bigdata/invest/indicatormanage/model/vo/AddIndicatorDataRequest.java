package com.htf.bigdata.invest.indicatormanage.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @description: 上传指标数据
 * @author: panpei
 * @date: 2019/6/17
 */
@ApiModel("上传指标数据")
@Data
public class AddIndicatorDataRequest {

    @ApiModelProperty("指标id")
    @NotNull(message = "参数[indicatorId]不为空")
    private Long indicatorId;

    @ApiModelProperty("用户id")
    @NotBlank(message = "参数[userId]不为空")
    private String userId;

    @ApiModelProperty("时间:yyyy-MM-dd")
    @NotBlank(message = "参数[indicatorDataTime]不为空")
    private String indicatorDataTime;

    @ApiModelProperty("指标数据值")
    @NotNull(message = "参数[indicatorData]不为空")
    private BigDecimal indicatorData;

}
