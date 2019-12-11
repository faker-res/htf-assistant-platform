package com.htf.bigdata.invest.platform.controller;

import com.htf.bigdata.invest.platform.model.bo.MessageBO;
import com.htf.bigdata.invest.platform.model.request.MessageBroadcastRequest;
import com.htf.bigdata.invest.platform.model.request.MessageSendRequest;
import com.htf.bigdata.invest.platform.model.request.MessageSetReadRequest;
import com.htf.bigdata.invest.platform.model.request.MessageUnreadRequest;
import com.htf.bigdata.invest.platform.model.response.ErrorResponse;
import com.htf.bigdata.invest.platform.model.response.MessageUnreadResponse;
import com.htf.bigdata.invest.platform.model.response.Response;
import com.htf.bigdata.invest.platform.service.IMessageService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 站内消息
 */
@RestController
@RequestMapping(path = "/message")
public class MessageController {

    private final static Logger logger = LogManager.getLogger(MessageController.class);

    @Autowired
    private IMessageService messageService;

    /**
     * 发送消息
     * @param param
     * @return
     */
    @ApiOperation("发送消息")
    @PostMapping(path = "/send")
    public Response send(@RequestBody MessageSendRequest param) {
        if (StringUtils.isEmpty(param.getUserId()) || StringUtils.isEmpty(param.getReceiver_userId())){
            return new ErrorResponse("缺少参数");
        }

        try {
            Boolean result = messageService.sendMessage(param);
            return new Response(result);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new ErrorResponse(e.getMessage());
        }
    }

    /**
     * 发送广播
     * @param param
     * @return
     */
    @ApiOperation("发送广播")
    @PostMapping(path = "/broadcast")
    public Response broadcast(@RequestBody MessageBroadcastRequest param) {
        try {
            Boolean result = messageService.sendBroadcast(null,null, null,param.getMessage());
            return new Response(result);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new ErrorResponse(e.getMessage());
        }
    }

    /**
     * 获取未读列表
     * @return
     */
    @GetMapping(path = "/unread")
    public Response unread(MessageUnreadRequest param) {
        try {
            MessageUnreadResponse messageUnreadResponse = messageService.getUnreadMessage(param);
            return new Response(messageUnreadResponse);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new ErrorResponse(e.getMessage());
        }
    }

    /**
     * 设置已读
     * @return
     */
    @PostMapping(path = "/setread")
    public Response setRead(@RequestBody MessageSetReadRequest param) {
        Boolean result = false;
        if (StringUtils.isNotEmpty(param.getMessage_ids())){
            List<Long> messageIds = new ArrayList<>();
            for (String item : param.getMessage_ids().split(",")){
                messageIds.add(Long.valueOf(item));
            }
            result = messageService.setRead(messageIds);
        }else if (StringUtils.isNotEmpty(param.getUserId())){
            result = messageService.setRead(param.getUserId());
        }
        return new Response(result);
    }

    /**
     * 撤回广播
     * @param message_id
     * @return
     */
    @GetMapping(path = "/revokebroadcast")
    public Response revokeBroadcast(Long message_id) {
        try {
            Boolean result = messageService.revokeBroadcast(message_id);
            return new Response(result);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new ErrorResponse(e.getMessage());
        }
    }
}
