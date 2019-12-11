package com.htf.bigdata.invest.indicatormanage.model.response.dto;

import com.htf.bigdata.invest.indicatormanage.model.vo.IndustryItem;
import com.htf.bigdata.invest.indicatormanage.model.vo.StockItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description: 指标基础信息返回值
 * @author: panpei
 * @date: 2019/6/12
 */
@Data
@ApiModel("指标基础信息")
public class IndicatorBaseInfoDto {

    @ApiModelProperty("指标/指标组id")
    private Long id;

    @ApiModelProperty("指标/指标组名称")
    private String indicatorName;

    @ApiModelProperty("指标单位")
    private String indicatorUnit;

    @ApiModelProperty("更新频率")
    private String indicatorFrequency;

    @ApiModelProperty("数据来源")
    private String dataSource;

    @ApiModelProperty("指标类型")
    private Integer indicatorType;

    @ApiModelProperty("更新状态")
    private String updateStatus;

    @ApiModelProperty("通知状态")
    private String noticeStatus;

    @ApiModelProperty("最近数据时间")
    private Date dataRecentTime;

    @ApiModelProperty("关联的研究员姓名")
    private List<String> researchers;

    @ApiModelProperty("关联的股票")
    private List<StockItem> stocks;

    @ApiModelProperty("关联的行业")
    private List<IndustryItem> industries;

    @ApiModelProperty("是否是管理者")
    private Boolean manager;

    @ApiModelProperty("图标配置")
    private String chartSetting;

    @ApiModelProperty("发布图表的数据开始时间")
    private Date startTime;

    @ApiModelProperty("发布图表的数据结束时间")
    private Date endTime;

    @ApiModelProperty("最后一个数据与前一个涨幅")
    private BigDecimal rate;
}
