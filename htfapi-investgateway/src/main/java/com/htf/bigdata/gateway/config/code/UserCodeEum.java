package com.htf.bigdata.gateway.config.code;

public enum UserCodeEum implements ICodeConfig {

    ERROR_USER_PARAMETER_EMPTY(4101, "user parameter is empty"),
    ERROR_USER_PARAMETER(4102, "user parameter is error"),
    ERROR_SSO(4103, "cas error")
    ;

    private int code;

    private String message;

    private UserCodeEum(int code, String message) {
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
