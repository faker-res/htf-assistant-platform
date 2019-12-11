package com.htf.bigdata.gateway.config.code;

/**
 * 错误码
 * @author xwu.abcft
 */
public enum BaseCodeEnum implements ICodeConfig {

	SUCCESS(200,"success"),




	ERROR_REQUEST_URL(4001, "gateway : error request url"),



	ERROR_DEFAULT(5000, "gatway : error"),
	ERROR_SERVICE(5001,"gateway : unavailable service"),


	;

	private int code;

	private String message;

	private BaseCodeEnum(Integer code, String message) {
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
