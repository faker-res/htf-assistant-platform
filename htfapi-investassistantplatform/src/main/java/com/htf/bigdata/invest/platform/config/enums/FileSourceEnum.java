package com.htf.bigdata.invest.platform.config.enums;

public enum FileSourceEnum {

    OSS("阿里云存储"),
    LOCAL("本地文件"),;

    private String chineseName;

    FileSourceEnum(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getChineseName() {
        return chineseName;
    }
}
