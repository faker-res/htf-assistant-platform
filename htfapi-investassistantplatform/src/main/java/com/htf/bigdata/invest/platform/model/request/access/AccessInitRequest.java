package com.htf.bigdata.invest.platform.model.request.access;

import com.htf.bigdata.invest.platform.model.bo.account.AccessTreeBO;
import lombok.Data;

import java.util.List;

@Data
public class AccessInitRequest {

    private String company_name;

    private String manager_user_id;

    private List<AccessTreeBO> auth_tree;
}
