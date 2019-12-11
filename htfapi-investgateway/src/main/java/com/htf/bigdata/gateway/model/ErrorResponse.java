package com.htf.bigdata.gateway.model;

import com.htf.bigdata.gateway.config.code.BaseCodeEnum;
import com.htf.bigdata.gateway.config.code.ICodeConfig;

public class ErrorResponse<T> extends Response {

	{
		setCode(BaseCodeEnum.ERROR_DEFAULT.getCode());
		setMessage(BaseCodeEnum.ERROR_DEFAULT.getMessage());
	}

	public ErrorResponse() {
	}

	public ErrorResponse(Integer code, String message) {
		setCode(code);
		setMessage(message);
	}

	public ErrorResponse(String message) {
		setMessage(message);
	}

	public ErrorResponse(Integer code, T data) {
		setCode(code);
	}

	public ErrorResponse(T data) {
		setData(data);
	}

	public ErrorResponse(ICodeConfig code) {
		setCode(code.getCode());
		setMessage(code.getMessage());
	}
}
