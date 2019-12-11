package com.htf.bigdata.invest.indicatormanage.config.enums;

/**
 * @description: 指标的数据状态
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public enum IndicatorDataStatusEnum {
    // 未确认/待更新
    UNCONFIRMED(0),
    // 已确认/已更新
    CONFIRMED(1),
    // 停止更新
    STOP(2);

    private int status;

    IndicatorDataStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
