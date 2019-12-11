package com.htf.bigdata.invest.indicatormanage.model.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class InvestnewIndexIndicatorText extends BaseDomain {

    private Long indicatorId;

    private Date indicatorTime;

    private String indicatorTextData;

}