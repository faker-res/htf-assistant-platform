package com.htf.bigdata.invest.indicatormanage.config.code;

/**
 * @description: 指标管理状态码
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public enum IndicatorManageCodeConfig implements ICodeConfig {
    ERROR_INDICATOR_NAME_EXIST(51001, "indicator name exist"),
    ERROR_INDICATOR_DATA_EXIST(51002, "indicator data exist"),
    ERROR_INDICATOR_NOT_EXIST(51003, "indicator not exist"),
    ERROR_INDICATOR_DATA_TIME_BEYOND(51004, "indicator data time beyond"),
    ERROR_INDICATOR_DATA_TIME_EXIST(51005, "indicator data time exist"),
    ERROR_INDICATOR_BATCH_IMPORT_ERROR(51006, "indicator batch import error")
    ;
    private int code;

    private String message;

    private IndicatorManageCodeConfig(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
