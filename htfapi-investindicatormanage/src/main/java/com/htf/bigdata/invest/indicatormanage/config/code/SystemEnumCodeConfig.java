package com.htf.bigdata.invest.indicatormanage.config.code;

/**
 * @description: 通用状态码
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public enum SystemEnumCodeConfig implements ICodeConfig {
    SUCCESS(200, "success"),
    ERROR_SERVER(50000, "server error"),
    ERROR_VALIDATOR_PARAMETER(40000, "error validator parameter"),
    ERROR_REQUEST_URL(40001, "error request url"),
    ERROR_METHOD_NOT_SUPPORTED(40002, "method not supported"),
    ERROR_DATA_EXIST(40003, "exist data"),
    ERROR_AUTHORIZED(40004, "authorized error"),
    ;
    private int code;

    private String message;

    private SystemEnumCodeConfig(int code, String message) {
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
