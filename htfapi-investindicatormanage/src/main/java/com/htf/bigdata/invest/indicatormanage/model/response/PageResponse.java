package com.htf.bigdata.invest.indicatormanage.model.response;

import com.htf.bigdata.invest.indicatormanage.config.code.SystemEnumCodeConfig;
import lombok.Data;

import java.util.List;

/**
 * @description: 返回值格式
 * @author: xuyali
 * @date: 2019-06-17 17:22:00
 */
@Data
public class PageResponse<T> {

    private long totalSize;//数据总条数

    private int totalPage;//页码总数

    private int currentPage;//当前页

    protected List<T> data;

    protected Integer code = SystemEnumCodeConfig.SUCCESS.getCode();

    protected String message = "";

    public PageResponse() {
    }

    public PageResponse(String message) {
        this.message = message;
    }

    public PageResponse(List<T> data) {
        this.data = data;
    }

    public PageResponse(List<T> data, String message) {
        this.data = data;
        this.message = message;
    }

    public PageResponse(List<T> data, Integer code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public PageResponse(Integer code, String message) {
        this.code = code;
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
