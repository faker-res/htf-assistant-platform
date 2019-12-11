package com.htf.bigdata.invest.platform.config.enums;

/**
 * K线类型
 */
public enum LineTypeEnum {
    //1分钟线
    MIN1("1",1,"K1"),
    //5分钟线
    MIN5("5",5,"K5"),
    //15分钟线
    MIN15("15",15,"K15"),
    //30分钟线
    MIN30("30",30,"K30"),
    //60分钟线
    MIN60("60",60,"K60"),
    //日线
    DAY("DAY",1440,"K_DAY"),
    //周线
    WEEK("WEEK",10080,"K_WEEK"),
    //月线
    MONTH("MONTH",40320,"K_MONTH"),
    //季度线
    QUARTER("QUARTER",120960,"K_MONTH"),
    //半年线
    HALFYEAR("HALFYEAR",241920,"K_YEAR"),
    //年线
    YEAR("YEAR",483840,"K_YEAR");

    private String tag;

    private Integer minuteInterval;

    private String tagV2;

    LineTypeEnum(String tag, Integer minuteInterval, String tagV2) {
        this.tag = tag;
        this.minuteInterval = minuteInterval;
        this.tagV2 = tagV2;
    }

    public String getTag() {
        return tag;
    }

    public Integer getMinuteInterval() {
        return minuteInterval;
    }

    public String getTagV2() {
        return tagV2;
    }
}
