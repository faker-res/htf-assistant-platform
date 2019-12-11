package com.htf.bigdata.invest.indicatormanage.model.response.client;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @description: 账号信息
 * @author: panpei
 * @date: 2019/6/3 确认指标数据请求参数
 */
@Data
public class AccountResponse {

    // 主键
    //private Integer id;

    // 用户id
    private String user_id;

    // 用户关联公司
    private CompanyResponse company;

    // 用户名称
    private String username;

    // 邮箱
    private String email;

    // 手机号
    private String mobile;

    // 昵称
    private String nickname;

    // 头像
    private String avatar;

    // 状态
    private String status;

    // 权限
    private List<RoleResponse> roles;

    // 创建时间
    private Date create_time;

    //部门
    private String department;

    //职位
    private String position;
}
