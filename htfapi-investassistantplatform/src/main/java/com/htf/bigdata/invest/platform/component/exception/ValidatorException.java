package com.htf.bigdata.invest.platform.component.exception;

import com.htf.bigdata.invest.platform.config.code.ICodeConfig;

public class ValidatorException extends Exception {

    private int code = 40000;

    public ValidatorException() {
        super();
    }

    public ValidatorException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ValidatorException(ICodeConfig object) {
        super(object.getMessage());
        this.code = object.getCode();
    }

    public int getCode() {
        return code;
    }
}
