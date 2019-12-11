package com.htf.bigdata.invest.indicatormanage.config.enums;

/**
 * @description: 查询指标看板的类型
 * @author: xuyali
 * @date: 2019-06-13 17:22:00
 */
public enum QueryBoardTypeEnum {
    //  我的管理
    MANAGER(1),
    // 我的关注
    ATTENTION(2),
    // 全部指标
    ALL(3),
    //按行业查询
    INDUSTRY(4),
    //按关键词查询
    KEYWORD(5);

    private int queryType;

    QueryBoardTypeEnum(int queryType) {
        this.queryType = queryType;
    }

    public int getQueryType() {
        return queryType;
    }
}
