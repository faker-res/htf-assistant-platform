package com.htf.bigdata.invest.platform.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("发送消息")
@Data
public class MessageSendRequest {

    @ApiModelProperty(value = "发送人userId",example = "1063")
    private String userId;

    @ApiModelProperty(value = "接收人userId，支持批量发送，英文逗号分隔",example = "20688834,20688835")
    private String receiver_userId;

    @ApiModelProperty(value = "消息内容",example = "你好呀")
    private String message;

    @ApiModelProperty(value = "关联对象id",example = "456")
    private String object_id;

    @ApiModelProperty(value = "关联对象类型",example = "indicator")
    private String object_type;

    @ApiModelProperty(value = "关联对象父id",example = "12")
    private String object_pid;
}
