package com.htf.bigdata.invest.indicatormanage.model.response;

import com.htf.bigdata.invest.indicatormanage.config.code.ICodeConfig;
import com.htf.bigdata.invest.indicatormanage.config.code.SystemEnumCodeConfig;

/**
 * @description: 错误返回值格式
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public class ErrorResponse<T> extends Response {

	{
		setCode(SystemEnumCodeConfig.ERROR_SERVER.getCode());
		setMessage(SystemEnumCodeConfig.ERROR_SERVER.getMessage());
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
