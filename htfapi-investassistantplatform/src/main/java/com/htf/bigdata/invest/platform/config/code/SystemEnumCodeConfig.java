package com.htf.bigdata.invest.platform.config.code;

public enum SystemEnumCodeConfig implements ICodeConfig {
    SUCCESS(200, "success"),
    ERROR_SERVER(50000, "server error"),
    ERROR_VALIDATOR_PARAMETER(40000, "error validator parameter"),
    ERROR_REQUEST_URL(40001, "error request url"),
    ERROR_METHOD_NOT_SUPPORTED(40002, "method not supported"),
    ERROR_DATA_EXIST(40003, "exist data"),
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
