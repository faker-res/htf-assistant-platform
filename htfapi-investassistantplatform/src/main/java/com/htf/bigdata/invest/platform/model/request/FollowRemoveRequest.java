package com.htf.bigdata.invest.platform.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("取消关注")
@Data
public class FollowRemoveRequest {

    @ApiModelProperty(value = "用户id",example = "1063")
    private String userId;

    @ApiModelProperty(value = "关注对象id",example = "17,28")
    private String object_id;

    @ApiModelProperty(value = "关注类型：1-指标；2-看板",example = "1")
    private Integer type;
}
