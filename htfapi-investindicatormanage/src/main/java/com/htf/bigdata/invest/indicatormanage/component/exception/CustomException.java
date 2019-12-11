package com.htf.bigdata.invest.indicatormanage.component.exception;

import com.htf.bigdata.invest.indicatormanage.config.code.ICodeConfig;

/**
 * @description: 自定义异常类
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public class CustomException extends RuntimeException{

    private Integer code;

    private String message;

    public CustomException(ICodeConfig codeEnum){
        super();
        setCode(codeEnum.getCode());
        setMessage(codeEnum.getMessage());
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
