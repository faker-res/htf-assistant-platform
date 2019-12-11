package com.htf.bigdata.invest.indicatormanage.component.exception;

import com.htf.bigdata.invest.indicatormanage.config.code.ICodeConfig;

/**
 * @description: 参数异常类
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public class ValidatorException extends RuntimeException {

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
