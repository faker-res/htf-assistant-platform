package com.htf.bigdata.invest.platform.model.bo.data;

import lombok.Data;

@Data
public class AnalystBO {

    private String peo_uni_code;

    private String name;

    private String analyst_code;

    private CompanyBO company;
}
