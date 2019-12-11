package com.htf.bigdata.invest.indicatormanage.model.response.client;

import com.htf.bigdata.invest.indicatormanage.config.enums.CompanyTypeEnum;

import lombok.Data;

/**
 * @description: 用户关联公司信息
 * @author: panpei
 * @date: 2019/6/3
 */
@Data
public class CompanyResponse {

    // 公司id
    private Long company_id;

    // 公司简称
    private String sname;

    // 公司全称
    private String fullname;

    // 公司类型
    private CompanyTypeEnum type;
}
