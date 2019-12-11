package com.htf.bigdata.invest.platform.model.bo;

import lombok.Data;

import java.util.Date;

@Data
public class FeedbackBO {

    private Long feedbackId;

    private String userId;

    private String content;

    private String contactInfo;

    private String contacts;

    private Date finishTime;

    private Date createTime;
}
