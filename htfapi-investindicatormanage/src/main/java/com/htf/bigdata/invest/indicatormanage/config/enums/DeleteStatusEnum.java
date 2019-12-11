package com.htf.bigdata.invest.indicatormanage.config.enums;

/**
 * @description: 删除状态
 * @author panpei
 * @date 2019/6/15 16:46
 */
public enum DeleteStatusEnum {
    //未删除
    UNDELETE("0"),
    //已删除
    DELETE("1");
    private String deleteStatus;
    DeleteStatusEnum(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }}
