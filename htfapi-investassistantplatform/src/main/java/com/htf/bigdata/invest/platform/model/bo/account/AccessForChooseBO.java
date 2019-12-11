package com.htf.bigdata.invest.platform.model.bo.account;

import lombok.Data;

@Data
public class AccessForChooseBO {

    private String access_id;

    private String name;

    private String description;

    private Boolean chosen;
}
