package com.htf.bigdata.invest.platform.model.request.access;

import lombok.Data;

@Data
public class AccessModifyRoleRequest {

    private String userId;

    private Integer role_id;

    private String name;

    private String description;

    private String access_ids;

    private String status;
}
