package com.htf.bigdata.invest.indicatormanage.config.enums;

/**
 * @description: 指标的更新状态
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public enum IndicatorUpdateStatusEnum {
    OPEN("0"),
    CLOSE("1");

    private String updateStatus;

    IndicatorUpdateStatusEnum(String updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getUpdateStatus() {
        return updateStatus;
    }

    /**
     * 校验传入的noticeStatus谁否是枚举值
     * @param updateStatus
     * @return
     */
    public static Boolean check(String updateStatus) {
        for (IndicatorUpdateStatusEnum statusEnum : IndicatorUpdateStatusEnum.values()) {
            if (statusEnum.getUpdateStatus().equals(updateStatus)) {
                return true;
            }
        }
        return false;
    }
}
