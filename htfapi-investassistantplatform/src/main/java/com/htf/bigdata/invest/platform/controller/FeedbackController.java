package com.htf.bigdata.invest.platform.controller;

import com.htf.bigdata.invest.platform.model.bo.FeedbackBO;
import com.htf.bigdata.invest.platform.model.request.FeedbackAddRequest;
import com.htf.bigdata.invest.platform.model.request.FeedbackFinishRequest;
import com.htf.bigdata.invest.platform.model.response.ErrorResponse;
import com.htf.bigdata.invest.platform.model.response.FeedbackListResponse;
import com.htf.bigdata.invest.platform.model.response.Response;
import com.htf.bigdata.invest.platform.service.IFeedbackService;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 反馈
 */
@RestController
@RequestMapping(path = "/feedback")
public class FeedbackController {

    private final static Logger logger = LogManager.getLogger(FeedbackController.class);

    @Autowired
    private IFeedbackService feedbackService;

    /**
     * 新增反馈
     * @return
     */
    @ApiOperation("新增反馈")
    @PostMapping(path = "/add")
    Response add(@RequestBody FeedbackAddRequest request) {
        try{
            Boolean result = feedbackService.add(request.getUserId(),request.getContent(),request.getContact_info());
            return new Response(result);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new ErrorResponse(e.getMessage());
        }
    }

    /**
     * 解决反馈
     * @return
     */
    @ApiOperation("解决反馈")
    @PostMapping(path = "/finish")
    Response finish(@RequestBody FeedbackFinishRequest request) {
        try{
            Boolean result = feedbackService.finish(request.getFeedback_id());
            return new Response(result);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new ErrorResponse(e.getMessage());
        }
    }

    /**
     * 反馈列表
     * @return
     */
    @ApiOperation("反馈列表")
    @GetMapping(path = "/list")
    Response list(@RequestParam(value = "offset",defaultValue = "0") Integer offset,@RequestParam(value = "limit",defaultValue = "10000") Integer limit) {
        try{
            FeedbackListResponse feedbackListResponse =  feedbackService.get(offset,limit);
            return new Response(feedbackListResponse);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new ErrorResponse(e.getMessage());
        }
    }
}
