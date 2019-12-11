package com.htf.bigdata.invest.indicatormanage.config.enums;

public enum IndicatorCommentTypeEnum {
    //指标数值点评
    INDICATOR_DATA(1),
    // 特殊指标点评
    SPECIAL_INDICATOR(2);

    private Integer type;

    IndicatorCommentTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
