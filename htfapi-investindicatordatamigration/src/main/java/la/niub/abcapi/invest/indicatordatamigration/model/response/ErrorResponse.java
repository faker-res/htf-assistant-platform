package la.niub.abcapi.invest.indicatordatamigration.model.response;

import la.niub.abcapi.invest.indicatordatamigration.config.code.ICodeConfig;
import la.niub.abcapi.invest.indicatordatamigration.config.code.SystemEnumCodeConfig;

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
