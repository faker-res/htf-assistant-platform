package com.htf.bigdata.invest.platform.model.request;

import lombok.Data;

@Data
public class MessageSetReadRequest {

    private String userId;

    private String message_ids;
}
