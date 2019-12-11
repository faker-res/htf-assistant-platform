package com.htf.bigdata.invest.indicatormanage.config.enums;

/**
 * @description: 指标看板筛选状态
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public enum IndicatorQueryFlagEnum {

    // 未确认/待更新
    UNCONFIRMED("0"),
    // 已确认/已更新
    CONFIRMED("1");

    private String queryFlag;

    IndicatorQueryFlagEnum(String queryFlag) {
        this.queryFlag = queryFlag;
    }

    public String getQueryFlag() {
        return queryFlag;
    }
}
