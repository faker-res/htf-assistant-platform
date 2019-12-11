package com.htf.bigdata.invest.indicatormanage.config.enums;

/**
 * @description: 公司类型
 * @author: panpei
 * @date: 2019/6/3
 */
public enum CompanyTypeEnum {

    BROKER("券商"),
    FUND("基金"),;

    private String chineseName;

    CompanyTypeEnum(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getChineseName() {
        return chineseName;
    }
}
