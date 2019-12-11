package com.htf.bigdata.invest.platform.controller;

import com.htf.bigdata.invest.platform.model.request.FollowAddRequest;
import com.htf.bigdata.invest.platform.model.request.FollowRemoveRequest;
import com.htf.bigdata.invest.platform.model.response.ErrorResponse;
import com.htf.bigdata.invest.platform.model.response.Response;
import com.htf.bigdata.invest.platform.service.IFollowService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 关注
 * @author wb-wuxiao
 */
@RestController
@RequestMapping(path = "/follow")
public class FollowController {

    private final static Logger logger = LogManager.getLogger(FollowController.class);

    @Autowired
    private IFollowService followService;

    /**
     * 添加关注
     * @param param
     * @return
     */
    @ApiOperation("添加关注")
    @PostMapping(path = "/add")
    public Response add(@RequestBody FollowAddRequest param) {
        try{
            if (StringUtils.isEmpty(param.getObject_id()) || param.getType() == null || StringUtils.isEmpty(param.getUserId())){
                return new ErrorResponse("缺少参数");
            }
            Boolean result = followService.addFollow(param);
            return new Response(result);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new ErrorResponse(e.getMessage());
        }
    }

    /**
     * 取消关注
     * @param param
     * @return
     */
    @ApiOperation("取消关注")
    @DeleteMapping(path = "/remove")
    public Response remove(@RequestBody FollowRemoveRequest param) {
        try{
            if (StringUtils.isEmpty(param.getObject_id()) || param.getType() == null || StringUtils.isEmpty(param.getUserId())){
                return new ErrorResponse("缺少参数");
            }
            Boolean result = followService.removeFollow(param);
            return new Response(result);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new ErrorResponse(e.getMessage());
        }
    }
}
