package com.htf.bigdata.invest.platform.model.bo.account;

import com.htf.bigdata.invest.platform.model.bo.data.CompanyBO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AccountBO {

//    private Integer id;

    private String user_id;

    private CompanyBO company;

    private String username;

    private String email;

    private String mobile;

    private String nickname;

    private String avatar;

    private String status;

    private List<RoleBO> roles;

    private Date create_time;

    //部门
    private String department;

    //职位
    private String position;
}
