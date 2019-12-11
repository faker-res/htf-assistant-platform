package com.htf.bigdata.invest.platform.model.request.account;

import com.htf.bigdata.invest.platform.config.enums.CompanyTypeEnum;
import lombok.Data;

@Data
public class AccountCompleteInfoRequest {

    private String userId;

    private String nickname;

    private String email;

    private Long company_id;

    private CompanyTypeEnum company_type;
}
