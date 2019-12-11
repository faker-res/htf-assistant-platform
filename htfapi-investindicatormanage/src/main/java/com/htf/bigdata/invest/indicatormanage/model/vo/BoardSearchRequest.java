package com.htf.bigdata.invest.indicatormanage.model.vo;

import com.htf.bigdata.invest.indicatormanage.config.enums.QueryBoardTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xuyali
 * @date 2019/6/13 11:03
 */
@ApiModel("卡片搜索查询条件")
@Data
public class BoardSearchRequest {
    @ApiModelProperty("查询类型:我的管理 我的关注 全部指标 按行业划分 关键词搜索")
    QueryBoardTypeEnum type;
    @ApiModelProperty("一级行业名称")
    String firstIndustryName;
    @ApiModelProperty("二级行业名称")
    String secondIndustryName;
    @ApiModelProperty("关键词")
    String keyWord;
    @ApiModelProperty("是否按指标数值更新排序,默认true")
    Boolean orderByUpdate = true;
    @ApiModelProperty("页大小")
    int pageSize = 20;
    @ApiModelProperty("当前页码")
    int currentPage = 1;


}
