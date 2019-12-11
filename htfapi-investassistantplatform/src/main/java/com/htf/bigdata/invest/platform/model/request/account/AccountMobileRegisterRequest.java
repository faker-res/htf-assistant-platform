package com.htf.bigdata.invest.platform.model.request.account;

import com.htf.bigdata.invest.platform.config.CommonConfig;
import lombok.Data;

@Data
public class AccountMobileRegisterRequest {

    private String nickname;

    private String email;

    private String mobile;

    private String mobile_code;

    private String password =  CommonConfig.DEFAULT_PASSWORD;
}
