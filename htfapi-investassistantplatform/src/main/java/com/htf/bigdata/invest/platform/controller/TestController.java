package com.htf.bigdata.invest.platform.controller;

import com.htf.bigdata.invest.platform.component.websocket.MessageEndpoint;
import com.htf.bigdata.invest.platform.config.enums.CompanyTypeEnum;
import com.htf.bigdata.invest.platform.model.request.MessageSendRequest;
import com.htf.bigdata.invest.platform.model.response.Response;
import com.htf.bigdata.invest.platform.service.IMessageService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 测试用testest
 */
@RestController
@RequestMapping(path = "/test")
public class TestController {

    private final static Logger logger = LogManager.getLogger(TestController.class);

    @Value("${server.port}")
    private Integer serverPort;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private MessageEndpoint messageEndpoint;

    public static void main(String[] args) throws Exception {
        ;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    String hello() {
        return "this is test "+serverPort;
    }

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    String test() {
        return "this is test123321!!!!";
    }

    @GetMapping(path = "/wssendall")
    Response wssendall(String message, String userId) {
        if (StringUtils.isEmpty(message)){
            message = new Date().toString();
        }
        if (StringUtils.isEmpty(userId)){
            messageEndpoint.sendMessageToAll(message);
        }else{
            messageEndpoint.sendMessage(userId,message);
        }
        return new Response();
    }

    @GetMapping(path = "/sendbroadcast")
    Response sendBroadcast(String sender, Long companyId, String companyType, String message) {
        CompanyTypeEnum companyTypeEnum = null;
        try{
            companyTypeEnum = CompanyTypeEnum.valueOf(companyType.toUpperCase());
        }catch (Exception e){};
        Boolean result = messageService.sendBroadcast(sender,companyId,companyTypeEnum,message);
        return new Response(result);
    }

    @GetMapping(path = "/sendmessage")
    Response sendMessage(String sender, String receiver, String message) {
        MessageSendRequest request = new MessageSendRequest();
        request.setUserId(sender);
        request.setReceiver_userId(receiver);
        request.setMessage(message);
        Boolean result = messageService.sendMessage(request);
        return new Response(result);
    }
	
}
