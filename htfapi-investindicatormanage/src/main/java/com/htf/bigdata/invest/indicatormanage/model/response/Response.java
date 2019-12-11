package com.htf.bigdata.invest.indicatormanage.model.response;

import com.htf.bigdata.invest.indicatormanage.config.code.SystemEnumCodeConfig;

/**
 * @description: 返回值格式
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public class Response<T> {

    protected T data;

    protected Integer code = SystemEnumCodeConfig.SUCCESS.getCode();

    protected String message = "";

    public Response() {
    }

    public Response(String message) {
        this.message = message;
    }

    public Response(T data) {
        this.data = data;
    }

    public Response(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public Response(T data, Integer code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public Response(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Response{" +
                "data=" + data +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
