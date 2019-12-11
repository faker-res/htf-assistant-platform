package com.htf.bigdata.invest.platform.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("发送广播")
@Data
public class MessageBroadcastRequest {

    @ApiModelProperty(value = "消息内容",example = "你好呀")
    private String message;
}
