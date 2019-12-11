package com.htf.bigdata.invest.indicatormanage.config.enums;

/**
 * @description: 指标更新频率
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public enum IndicatorFrequencyEnum {

    DAY("日", 1),

    WEEK("周", 2),

    MONTH("月", 3),

    QUARTER("季度", 4),

    HALF_YEAR("半年", 5),

    YEAR("年", 6),

    OTHER("不定期", 7)
    ;

    private String frequency;

    private Integer sort;

    IndicatorFrequencyEnum(String frequency, Integer sort) {
        this.frequency = frequency;
        this.sort = sort;
    }

    public String getFrequency() {
        return frequency;
    }

    public Integer getSort() {
        return sort;
    }

    public static IndicatorFrequencyEnum valueOfString(String frequency) {
        switch (frequency) {
            case "日":
                return DAY;
            case "周":
                return WEEK;
            case "月":
                return MONTH;
            case "季度":
                return QUARTER;
            case "半年":
                return HALF_YEAR;
            case "年":
                return YEAR;
            default:
                return OTHER;
        }
    }
}
