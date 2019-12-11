package com.htf.bigdata.invest.platform.model.bo;

import lombok.Data;

import java.util.Date;

@Data
public class MessageBO {

    private Long messageId;

    private String senderUserId;

    private String receiverUserId;

    private String message;

    private Boolean isBroadcast;

    private Boolean isRead;

    private Date readTime;

    private String objectId;

    private String objectType;

    private String objectPid;

    private Date createTime;
}
