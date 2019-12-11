package com.htf.bigdata.invest.platform.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("新增反馈")
@Data
public class FeedbackAddRequest {

    @ApiModelProperty(value = "用户id",example = "1063")
    private String userId;

    @ApiModelProperty(value = "反馈内容",example = "我这里有个疑难杂症")
    private String content;

    @ApiModelProperty(value = "联系方式",example = "13770846442")
    private String contact_info;
}
