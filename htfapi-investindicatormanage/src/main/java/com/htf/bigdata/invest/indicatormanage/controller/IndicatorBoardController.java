package com.htf.bigdata.invest.indicatormanage.controller;

import com.alibaba.fastjson.JSON;
import com.htf.bigdata.invest.indicatormanage.model.response.PageResponse;
import com.htf.bigdata.invest.indicatormanage.model.response.Response;
import com.htf.bigdata.invest.indicatormanage.model.response.dto.IndicatorBaseInfoDto;
import com.htf.bigdata.invest.indicatormanage.model.response.dto.IndicatorBoardDto;
import com.htf.bigdata.invest.indicatormanage.model.response.dto.QueryIndicatorCountResponse;
import com.htf.bigdata.invest.indicatormanage.model.vo.BoardSearchRequest;
import com.htf.bigdata.invest.indicatormanage.model.vo.ConfirmDataInput;
import com.htf.bigdata.invest.indicatormanage.model.vo.PublishIndicatorRequest;
import com.htf.bigdata.invest.indicatormanage.model.vo.QueryIndicatorDataVo;
import com.htf.bigdata.invest.indicatormanage.service.IndicatorBoardService;
import com.htf.bigdata.invest.indicatormanage.service.IndicatorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 行业指标卡片接口
 *
 * @author xuyali createDate: 2019-06-13
 */
@RestController
@RequestMapping("/board")
public class IndicatorBoardController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IndicatorBoardService indicatorBoardService;

    @RequestMapping(value = "/cardList", method = RequestMethod.POST)
    @ApiOperation("根据条件查询指标卡片列表,分页显示")
    public PageResponse<IndicatorBoardDto> queryIndicatorCount(@RequestBody BoardSearchRequest request) throws Exception {
        PageResponse<IndicatorBoardDto> response = new PageResponse<IndicatorBoardDto>();
        int currentPage = request.getCurrentPage();
        long totalSize = indicatorBoardService.queryTotalCount(request);
        int totalPage = (int) (totalSize + request.getPageSize() - 1) / request.getPageSize();
        response.setCurrentPage(currentPage);
        response.setTotalPage(totalPage);
        response.setTotalSize(totalSize);
        request.setCurrentPage(currentPage);
        List<IndicatorBoardDto> indicatorBoardDtoList = indicatorBoardService.queryIndicatorBoardList(request);
        response.setData(indicatorBoardDtoList);
        return response;
    }

    @RequestMapping(value = "/saveDraft", method = RequestMethod.POST)
    @ApiOperation("保存指标组草稿")
    public Response saveDraft(@RequestBody PublishIndicatorRequest request) throws Exception {
        Response response = new Response();
        boolean success = indicatorBoardService.saveDraft(request);
        if (success) {
            response.setMessage("保存成功");
        }
        return response;
    }

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @ApiOperation("发布指标卡片")
    public Response publish(@RequestBody PublishIndicatorRequest request) throws Exception {
        Response response = new Response();
        boolean success = indicatorBoardService.publishIndicator(request);
        if (success) {
            response.setMessage("发布成功");
        }
        return response;
    }

    @RequestMapping(value = "/attentionIndicatorIds", method = RequestMethod.GET)
    @ApiOperation("查询已关注的指标ID列表")
    public Response attentionIndicatorIds(@ApiParam(name = "keyWord", value = "关键词") @RequestParam(name = "keyWord", required = false) String keyWord,
                                          @ApiParam(name = "firstIndustryName", value = "一级行业名称") @RequestParam(name = "firstIndustryName", required = false) String firstIndustryName) throws Exception {
        Response response = new Response();
        response.setData(indicatorBoardService.attentionIndicatorIdList(keyWord, firstIndustryName));
        return response;
    }

    @RequestMapping(value = "/indicatorlist", method = RequestMethod.GET)
    @ApiOperation("查询要添加关注的指标列表")
    public PageResponse<IndicatorBaseInfoDto> indicatorList(@ApiParam(name = "keyWord", value = "关键词") @RequestParam(name = "keyWord", required = false) String keyWord,
                                                              @ApiParam(name = "firstIndustryName", value = "一级行业") @RequestParam(name = "firstIndustryName", required = false) String firstIndustryName,
                                                              @ApiParam(name = "pageSize", value = "页大小") @RequestParam(name = "pageSize", required = false, defaultValue = "20") int pageSize,
                                                              @ApiParam(name = "currentPage", value = "当前页码") @RequestParam(name = "currentPage", required = false, defaultValue = "1") int currentPage) throws Exception {
        PageResponse<IndicatorBaseInfoDto> response = new  PageResponse<IndicatorBaseInfoDto>();
        long totalSize = indicatorBoardService.queryTotalCount(keyWord,firstIndustryName);
        int totalPage = (int) (totalSize + pageSize - 1) / pageSize;
        response.setCurrentPage(currentPage);
        response.setTotalSize(totalSize);
        response.setTotalPage(totalPage);
        response.setData(indicatorBoardService.allIndicatorList(keyWord, firstIndustryName, pageSize, currentPage));
        return response;
    }


}
