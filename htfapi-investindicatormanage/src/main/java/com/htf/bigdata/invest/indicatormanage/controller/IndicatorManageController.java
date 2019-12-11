package com.htf.bigdata.invest.indicatormanage.controller;

import com.alibaba.fastjson.JSON;
import com.htf.bigdata.invest.indicatormanage.config.enums.QueryIndicatorTypeEnum;
import com.htf.bigdata.invest.indicatormanage.model.response.Response;
import com.htf.bigdata.invest.indicatormanage.model.response.dto.*;
import com.htf.bigdata.invest.indicatormanage.model.vo.AddIndicatorGroupRequest;
import com.htf.bigdata.invest.indicatormanage.model.vo.AddIndicatorRequest;
import com.htf.bigdata.invest.indicatormanage.model.vo.HistoryContent;
import com.htf.bigdata.invest.indicatormanage.model.vo.UpdateIndicatorRequest;
import com.htf.bigdata.invest.indicatormanage.model.vo.*;
import com.htf.bigdata.invest.indicatormanage.service.IIndicatorManageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: 指标管理
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
@RestController
@RequestMapping("/index")
@Validated
public class IndicatorManageController {

    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private IIndicatorManageService indicatorManageService;

    /**
     * 查询待确认和待更新指标数量
     * @param userId
     * @return
     * @throws Exception
     */
    @GetMapping("/query-indicator-count")
    @ApiOperation("查询待确认和待更新指标数量")
    public Response<QueryIndicatorCountResponse> queryIndicatorCount(@ApiParam(name="userId", value = "用户Id") @RequestParam(name="userId")@NotBlank(message = "参数[userId]不为空") String userId) throws Exception {
        logger.info("param: userId:{}", userId);
        QueryIndicatorCountResponse result = indicatorManageService.queryIndicatorCount(userId);
        return new Response(result);
    }

    /**
     * 查询行业指标和组合模板列表
     * @param userId
     * @param keyword
     * @param type
     * @return
     * @throws Exception
     */
    @GetMapping("/query-my-manage-indicator")
    @ApiOperation("查询行业指标和组合模板列表")
    public Response<IndicatorSummaryDto> queryMyManageIndicator(@ApiParam(name="userId", value = "用户Id") @RequestParam(name="userId") @NotBlank(message = "参数[userId]不能为空") String userId,
                                                                @ApiParam(name="keyword", value = "关键词") @RequestParam(name="keyword", required = false) String keyword,
                                                                @ApiParam(name="type", value = "类型") @RequestParam(name="type") @NotNull(message = "参数[type]不能为空") QueryIndicatorTypeEnum type) throws Exception {
        logger.info("param: userId:{}, keyword:{}, type:{}", userId, keyword, type);
        List<IndicatorSummaryDto> result = indicatorManageService.queryMyManageIndicator(userId, keyword, type);
        return new Response(result);
    }

    /**
     * 查询所有待更新指标数据
     * @param userId
     * @param page
     * @param limit
     * @return
     * @throws Exception
     */
    @GetMapping("/query-updated-indicator-data")
    @ApiOperation("查询所有待更新指标数据")
    public Response<QueryUpdatedIndicatorDataResponse> queryUpdatedIndicatorData(@ApiParam(name="userId", value = "用户Id") @RequestParam(name="userId") @NotBlank(message = "参数[userId]不能为空") String userId,
                                                                                 @ApiParam(name="page", value = "当前页") @RequestParam(defaultValue = "1") Integer page,
                                                                                 @ApiParam(name="limit", value = "每页记录数") @RequestParam(defaultValue = "4") Integer limit) throws Exception {
        logger.info("param: userId:{}, page:{}, limit:{}", userId, page, limit);
        List<QueryUpdatedIndicatorDataResponse> result = indicatorManageService.queryUpdatedIndicatorData(userId, page, limit);
        return new Response(result);
    }

    /**
     * 新建基础指标
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/add-indicator")
    @ApiOperation("新建基础指标")
    public Response saveIndicator(@RequestBody @Valid AddIndicatorRequest request) throws Exception {
        logger.info("param:{}", JSON.toJSONString(request));
        Long specialGroupId = indicatorManageService.saveIndicator(request);
        return new Response(specialGroupId);
    }

    /**
     * 根据关键词查询股票列表
     * @param keyword
     * @param limit
     * @return
     * @throws Exception
     */
    @GetMapping("/query-stock")
    @ApiOperation("根据关键词查询股票列表")
    public Response<StockDto> queryStock(@ApiParam(name="keyword", value = "关键词") @RequestParam(name="keyword") @NotBlank(message = "参数[keyword]不能为空") String keyword,
                               @ApiParam(name="limit", value = "显示个数") @RequestParam(name="limit", required = false) Integer limit) throws Exception {
        logger.info("param: keyword:{}, limit:{}", keyword, limit);
        List<StockDto> result = indicatorManageService.queryStock(keyword, limit);
        return new Response(result);
    }

    /**
     * 查询行业列表
     * @return
     * @throws Exception
     */
    @GetMapping("/query-industry")
    @ApiOperation("查询行业列表")
    public Response<IndustryDto> queryIndustry() throws Exception {
        List<IndustryDto> result = indicatorManageService.queryIndustry();
        return new Response(result);
    }

    /**
     * 解析新建基础指标上传的历史数据
     * @param importContent
     * @return
     * @throws Exception
     */
    @PostMapping("/parse-indicator-data")
    @ApiOperation("解析新建基础指标上传的历史数据")
    public Response<ParseIndicatorDataResponse> parseIndicatorData(@RequestBody HistoryContent importContent) throws Exception {
        logger.info("param: importContent:{}", importContent.getContent());
        ParseIndicatorDataResponse result = indicatorManageService.parseIndicatorData(importContent.getContent());
        return new Response(result);
    }

    /**
     * 查询指标/指标组基础信息
     * @param userId
     * @param indicatorId
     * @return
     * @throws Exception
     */
    @GetMapping("/query-indicator-basicinfo")
    @ApiOperation("查询指标/指标组基础信息")
    public Response<IndicatorBaseInfoDto> queryIndicatorBasicInfo(@ApiParam(name="userId", value = "用户Id") @RequestParam(name="userId") @NotBlank(message = "参数[userId]不能为空") String userId,
                                                                  @ApiParam(name="indicatorId", value = "指标/指标组Id") @RequestParam(name="indicatorId") @NotNull(message = "参数[indicatorId]不能为空") Long indicatorId) throws Exception {
        logger.info("param: userId:{}, indicatorId:{}", userId, indicatorId);
        IndicatorBaseInfoDto result = indicatorManageService.queryIndicatorBasicInfo(userId, indicatorId);
        return new Response(result);
    }

    /**
     * 编辑指标/指标组信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/update-indicator-basicinfo")
    @ApiOperation("修改指标/指标组基础信息")
    public Response updateIndicatorBasicInfo(@RequestBody @Valid UpdateIndicatorRequest request) throws Exception {
        logger.info("param: {}", JSON.toJSONString(request));
        indicatorManageService.updateIndicatorBasicInfo(request);
        return new Response();
    }

    /**
     * 查询特殊指标组（基础指标）/指标组图数据信息
     * @param startTime
     * @param endTime
     * @param indicatorId
     * @return
     * @throws Exception
     */
    @GetMapping("/query-indicator-group-chart")
    @ApiOperation("查询特殊指标组（基础指标）/指标组图数据信息")
    public Response<IndicatorChartDetailDto> queryIndicatorGroupChart(@ApiParam(name="startTime", value = "开始时间") @RequestParam(name="startTime", required = false) String startTime,
                                                                @ApiParam(name="endTime", value = "结束时间") @RequestParam(name="endTime", required = false) String endTime,
                                                                @ApiParam(name="indicatorId", value = "指标Id") @RequestParam(name="indicatorId") @NotNull(message = "参数[indicatorId]不能为空") Long indicatorId) throws Exception {
        logger.info("param: starTime:{}, endTime:{}, indicatorId:{}", startTime, endTime, indicatorId);
        List<IndicatorChartDetailDto> result = indicatorManageService.queryIndicatorGroupChart(startTime, endTime, indicatorId);
        return new Response(result);
    }

    /**
     * 查询基础指标的图数据信息
     * @param indicatorIds
     * @return
     * @throws Exception
     */
    @GetMapping("/query-indicator-chart")
    @ApiOperation("查询基础指标的图数据信息")
    public Response<IndicatorChartDetailDto> queryIndicatorChart(@ApiParam(name="indicatorIds", value = "指标Id,多个逗号分割") @RequestParam(name="indicatorIds") @NotBlank(message = "参数[indicatorIds]不能为空") String indicatorIds) throws Exception {
        logger.info("param: indicatorIds: {}", indicatorIds);
        List<IndicatorChartDetailDto> result = indicatorManageService.queryIndicatorChart(indicatorIds);
        return new Response(result);
    }

    /**
     * 新建组合模板
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/add-indicator-group")
    @ApiOperation("新建组合模板")
    public Response addIndicatorGroup(@RequestBody @Valid AddIndicatorGroupRequest request) throws Exception {
        logger.info("param: {}", JSON.toJSONString(request));
        Long groupId = indicatorManageService.addIndicatorGroup(request);
        return new Response(groupId);
    }

    /**
     * 查询所有基础指标
     * @param indicatorId
     * @return
     * @throws Exception
     */
    @GetMapping("/query-all-basic-indicator")
    @ApiOperation("查询所有基础指标")
    public Response<QueryAllBasicIndicatorDto> queryAllBasicIndicator(@ApiParam(name="userId", value = "用户Id") @RequestParam(name="userId") @NotBlank(message = "参数[userId]不能为空") String userId,
                                                                      @ApiParam(name="indicatorId", value = "指标组id") @RequestParam(name="indicatorId", required = false) Long indicatorId) throws Exception {
        logger.info("param: userId:{}, indicatorId: {}", userId, indicatorId);
        List<QueryAllBasicIndicatorDto> result = indicatorManageService.queryAllBasicIndicator(userId, indicatorId);
        return new Response(result);
    }

    /**
     * 根据关键字搜索基础指标
     * @param keyword
     * @param indicatorId
     * @return
     * @throws Exception
     */
    @GetMapping("query-basic-indicator-keyword")
    @ApiOperation("根据关键字搜索基础指标")
    public Response<QueryAllBasicIndicatorInfoDto> queryBasicIndicatorByKeyword(@ApiParam(name="keyword", value = "关键词") @RequestParam(name="keyword") @NotBlank(message = "参数[keyword]不能为空") String keyword,
                                                                                @ApiParam(name="indicatorId", value = "指标组id") @RequestParam(name="indicatorId", required = false) Long indicatorId) throws Exception {
        logger.info("param: keyword:{}", keyword);
        List<QueryAllBasicIndicatorInfoDto> result = indicatorManageService.queryBasicIndicatorByKeyword(keyword, indicatorId);
        return new Response(result);
    }

    /**
     * 指标组添加指标
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("add-indicator-for-group")
    @ApiOperation("指标组添加指标")
    public Response addIndicatorForGroup(@RequestBody @Valid AddIndicatorForGroupRequest request) throws Exception {
        logger.info("param: {}", JSON.toJSONString(request));
        indicatorManageService.addIndicatorForGroup(request);
        return new Response();
    }

    /**
     * 指标组删除指标
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("delete-indicator-for-group")
    @ApiOperation("指标组删除指标")
    public Response deleteIndicatorForGroup(@RequestBody @Valid DeleteIndicatorForGroupRequest request) throws Exception {
        logger.info("param: {}", JSON.toJSONString(request));
        indicatorManageService.deleteIndicatorForGroup(request);
        return new Response();
    }

    /**
     * 更新待更新的指标数据
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("update-indicator-data")
    @ApiOperation("更新待更新的指标数据")
    public Response updateIndicatorData(@RequestBody @Valid UpdateIndicatorDataRequest request) throws Exception {
        logger.info("param: {}", JSON.toJSONString(request));
        indicatorManageService.updateIndicatorData(request);
        return new Response();
    }

    /**
     * 单个上传指标数据
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("add-indicator-data")
    @ApiOperation("单个上传指标数据")
    public Response addIndicatorData(@RequestBody @Valid AddIndicatorDataRequest request) throws Exception {
        logger.info("param: {}", JSON.toJSONString(request));
        Long indicatorDataId = indicatorManageService.addIndicatorData(request);
        return new Response(indicatorDataId);
    }
}
