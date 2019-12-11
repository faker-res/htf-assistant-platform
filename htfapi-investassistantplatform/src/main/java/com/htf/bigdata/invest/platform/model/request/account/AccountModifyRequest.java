package com.htf.bigdata.invest.platform.model.request.account;

import lombok.Data;

@Data
public class AccountModifyRequest {

    private String userId;

    private String nickname;

    private String password;

    private String role_ids;

    private String status;
}
