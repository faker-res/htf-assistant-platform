package com.htf.bigdata.invest.platform.model.response.account;

import com.htf.bigdata.invest.platform.model.bo.account.AccountBO;
import lombok.Data;

import java.util.List;

@Data
public class AccountListResponse {

    private Integer total;

    private List<AccountBO> list;
}
