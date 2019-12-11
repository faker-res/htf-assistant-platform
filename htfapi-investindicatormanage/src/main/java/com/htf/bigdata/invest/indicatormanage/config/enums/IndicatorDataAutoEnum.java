package com.htf.bigdata.invest.indicatormanage.config.enums;

/**
 * @description: 指标的数据来源
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public enum IndicatorDataAutoEnum {
    // 自动
    AUTO(1),
    // 手动
    NOT_AUTO(2);

    private int indicatorDataAuto;

    IndicatorDataAutoEnum(int indicatorDataAuto) {
        this.indicatorDataAuto = indicatorDataAuto;
    }

    public int getIndicatorDataAuto() {
        return indicatorDataAuto;
    }}
