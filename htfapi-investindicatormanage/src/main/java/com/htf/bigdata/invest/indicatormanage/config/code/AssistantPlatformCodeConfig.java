package com.htf.bigdata.invest.indicatormanage.config.code;

/**
 * @description: 平台接口状态码
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public enum AssistantPlatformCodeConfig implements ICodeConfig {
    USER_INFO_FAIL(520001, "get user info fail"),
    ;
    private int code;

    private String message;

    private AssistantPlatformCodeConfig(int code, String message) {
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
