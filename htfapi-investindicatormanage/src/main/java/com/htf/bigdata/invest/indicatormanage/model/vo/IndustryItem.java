package com.htf.bigdata.invest.indicatormanage.model.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @description: 创建指标/组请求参数-关联行业
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
@Data
public class IndustryItem {

    // investnew_index_industry id
    private Long id;

    // 行业代码 预留字段
    private String industryCode;

    // 行业名称
    private String industryName;

}
