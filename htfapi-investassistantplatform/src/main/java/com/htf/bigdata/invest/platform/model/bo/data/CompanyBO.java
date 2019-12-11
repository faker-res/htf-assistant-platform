package com.htf.bigdata.invest.platform.model.bo.data;

import com.htf.bigdata.invest.platform.config.enums.CompanyTypeEnum;
import lombok.Data;

@Data
public class CompanyBO {

    private Long company_id;

    private String sname;

    private String fullname;

    private CompanyTypeEnum type;
}
