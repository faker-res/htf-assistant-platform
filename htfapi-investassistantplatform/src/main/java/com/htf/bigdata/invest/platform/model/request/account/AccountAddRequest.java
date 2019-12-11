package com.htf.bigdata.invest.platform.model.request.account;

import com.htf.bigdata.invest.platform.config.CommonConfig;
import com.htf.bigdata.invest.platform.config.enums.StatusEnum;
import lombok.Data;

@Data
public class AccountAddRequest {

    private String userId;

    private String nickname;

    private String email;

    private String mobile;

    private String password = CommonConfig.DEFAULT_PASSWORD;

    private String role_ids;

    private String status = StatusEnum.ENABLE.getStatus();
}
