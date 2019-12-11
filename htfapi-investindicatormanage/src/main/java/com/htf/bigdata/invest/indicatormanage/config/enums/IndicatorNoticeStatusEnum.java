package com.htf.bigdata.invest.indicatormanage.config.enums;

/**
 * @description: 指标的通知状态
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public enum IndicatorNoticeStatusEnum {
    OPEN("0"),
    CLOSE("1");

    private String noticeStatus;

    IndicatorNoticeStatusEnum(String noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public String getNoticeStatus() {
        return noticeStatus;
    }

    /**
     * 校验传入的noticeStatus谁否是枚举值
     * @param noticeStatus
     * @return
     */
    public static Boolean check(String noticeStatus) {
        for (IndicatorNoticeStatusEnum statusEnum : IndicatorNoticeStatusEnum.values()) {
            if (statusEnum.getNoticeStatus().equals(noticeStatus)) {
                return true;
            }
        }
        return false;
    }
}
