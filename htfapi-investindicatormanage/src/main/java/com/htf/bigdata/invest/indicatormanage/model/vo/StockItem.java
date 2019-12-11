package com.htf.bigdata.invest.indicatormanage.model.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @description: 创建指标/组请求参数-关联股票
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
@Data
public class StockItem {

    // investnew_index_stock id
    private Long id;

    // 股票代码
    @NotBlank(message = "参数[stockCode]不能为空")
    private String stockCode;

    // 股票名称
    @NotBlank(message = "参数[stockName]不能为空")
    private String stockName;
}
