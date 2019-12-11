package com.htf.bigdata.invest.indicatormanage.config.enums;

/**
 * @description: 衍生指标类型
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public enum DeriveIndicatorTypeEnum {
    // 同比
    YEAR_ON_YEAR(1),
    // 环比
    MONTH_ON_MONTH(2);

    private Integer type;

    DeriveIndicatorTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
