package com.htf.bigdata.invest.indicatormanage.model.vo;

import com.htf.bigdata.invest.indicatormanage.config.enums.SelectStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author xuyali
 * @date 2019/6/16 11:30
 */
@Data
@ApiModel("发布指标组请求")
public class PublishIndicatorRequest {
    @ApiModelProperty("指标组ID")
    private Long indicatorGroupId;
    @ApiModelProperty("发布开始日期")
    private Date startTime;
    @ApiModelProperty("发布结束日期")
    private Date endTime;
    @ApiModelProperty("指标发布状态信息列表")
    private List<IndicatorInfo> indicatorInfoList;

    @Data
    public static class IndicatorInfo{
        @ApiModelProperty("指标ID")
        private Long indicatorId;
        @ApiModelProperty("是否选择")
        private SelectStatusEnum statusEnum;
        @ApiModelProperty("发布的图配置")
        private String chartSetting;
    }

}
