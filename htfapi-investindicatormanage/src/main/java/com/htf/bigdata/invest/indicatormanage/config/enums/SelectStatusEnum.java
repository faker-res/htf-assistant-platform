package com.htf.bigdata.invest.indicatormanage.config.enums;

/**
 * @author xuyali
 * @date 2019/6/15 16:46
 */
public enum SelectStatusEnum {
    UNSELECT("0"),//未选
    SELECTED("1");//已选
    private String selectStatus;
    SelectStatusEnum(String selectStatus) {
        this.selectStatus = selectStatus;
    }

    public String getSelectStatus() {
        return selectStatus;
    }
}
