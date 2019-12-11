package com.htf.bigdata.invest.platform.model.request;

import lombok.Data;

@Data
public class MessageUnreadRequest {

    private String userId;

    private Long end_time;

    private Integer offset = 0;

    private Integer limit = 10;

    private String type;
}
