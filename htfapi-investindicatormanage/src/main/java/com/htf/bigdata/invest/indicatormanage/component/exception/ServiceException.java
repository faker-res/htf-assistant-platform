package com.htf.bigdata.invest.indicatormanage.component.exception;

import com.htf.bigdata.invest.indicatormanage.config.code.ICodeConfig;

/**
 * @description: 自定义service层异常类
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public class ServiceException extends RuntimeException {
    private int code = 50000;

    public ServiceException() {
        super();
    }

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(ICodeConfig object) {
        super(object.getMessage());
        this.code = object.getCode();
    }

    public int getCode() {
        return code;
    }
}
