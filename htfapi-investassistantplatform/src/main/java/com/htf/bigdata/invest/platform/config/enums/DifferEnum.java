package com.htf.bigdata.invest.platform.config.enums;

public enum DifferEnum {

    RISK("涨"),
    FALL("跌"),
    FAIR("平");

    private String name;

    DifferEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
