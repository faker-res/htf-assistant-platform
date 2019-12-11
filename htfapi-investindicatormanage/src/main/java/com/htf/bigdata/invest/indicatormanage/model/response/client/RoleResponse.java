package com.htf.bigdata.invest.indicatormanage.model.response.client;

import lombok.Data;

/**
 * @description: 用户关联角色信息
 * @author: panpei
 * @date: 2019/6/3
 */
@Data
public class RoleResponse {

    // 角色id
    private Integer role_id;

    // 角色名称
    private String name;

    // 角色状态
    private String status;
}
