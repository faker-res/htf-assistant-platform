package com.htf.bigdata.invest.indicatormanage.config.enums;

/**
 * @description: 指标的数据类型
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public enum IndicatorTypeEnum {
    // 基础指标
    BASIC_INDICATOR(1),
    // 指标组
    INDICATOR_GROUP(2),
    // 衍生指标
    DERIVE_INDICATOR(3),
    // 特殊指标组
    SPECIAL_INDICATOR_GROUP(4),
    // 特殊指标
    SPECIAL_INDICATOR(5);

    private int indicatorType;

    IndicatorTypeEnum(int indicatorType) {
        this.indicatorType = indicatorType;
    }

    public int getIndicatorType() {
        return indicatorType;
    }
}
