package com.htf.bigdata.gateway.model;

import com.htf.bigdata.gateway.config.code.BaseCodeEnum;

public class Response<T> {

    protected T data;

    protected Integer code = BaseCodeEnum.SUCCESS.getCode();

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
