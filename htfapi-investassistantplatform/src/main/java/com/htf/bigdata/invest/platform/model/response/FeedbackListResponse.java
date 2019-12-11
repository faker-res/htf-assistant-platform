package com.htf.bigdata.invest.platform.model.response;

import com.htf.bigdata.invest.platform.model.bo.FeedbackBO;
import lombok.Data;

import java.util.List;

@Data
public class FeedbackListResponse {

    private Integer total;

    private List<FeedbackBO> list;
}
