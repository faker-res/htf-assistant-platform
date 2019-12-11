package com.htf.bigdata.invest.platform.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("解决反馈")
@Data
public class FeedbackFinishRequest {

    @ApiModelProperty(value = "反馈id")
    private Long feedback_id;
}
