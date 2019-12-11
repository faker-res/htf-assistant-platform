package com.htf.bigdata.gateway.controller;

import com.htf.bigdata.gateway.component.client.IAssistanPlatformClient;
import com.htf.bigdata.gateway.model.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * cas登陆
 * @author wb-wuxiao
 */
@Controller
@RequestMapping(path = "/cas")
public class CasController {

    private final static Logger logger = LogManager.getLogger(CasController.class);

    //应用地址
    @Value("${security.cas.service.host}")
    private String serviceUrl;

    //应用登出地址
    @Value("${security.cas.service.logout}")
    private String serviceLogoutUrl;

    @Autowired
    private IAssistanPlatformClient assistanPlatformClient;

    /**
     * 获取cas用户
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping(path = "/getuser")
    String getUser(String redirect_url,Map<String, Object> model) {
        //获取cas用户
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String ticket = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(ticket)) {
            logger.info("username={}, ticket={}", username, ticket);
        }else{
            logger.error("user {} login failed with ticket {}", username, ticket);
            return "user "+username+" login failed with ticket "+ticket;//@todo 错误页
        }

        //根据cas的username获取用户id
        String userId = "";
        try{
            Response response = assistanPlatformClient.setToken(username,ticket);
            if (!response.getCode().equals(200) || response.getData() == null){
                return "error response:"+response;//@todo 错误页
            }
            userId = (String) response.getData();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return "error response:"+e.getMessage();//@todo 错误页
        }

        //跳转回前端地址
        redirect_url = StringUtils.defaultString(redirect_url,"");
        redirect_url = redirect_url.startsWith("http") ? redirect_url : serviceUrl+redirect_url;
        return "redirect:"+redirect_url+"?userId="+userId+"&token="+ticket;
	}

    /**
     * 登出回调
     * @param model
     * @return
     */
    @GetMapping(path = "/logout")
    String logout(Map<String, Object> model) {
        try{
            //获取cas用户
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            String ticket = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();

            Response response = assistanPlatformClient.removeToken(ticket);
            if (!response.getCode().equals(200) || response.getData() == null){
                return "error response:"+response;//@todo 错误页
            }
            logger.info("{} logout!",username);
            return "redirect:"+serviceLogoutUrl;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return "error response:"+e.getMessage();//@todo 错误页
        }
    }

//    @GetMapping(path = "/getusertest")
//    @ResponseBody
//    Response getUserTest(String redirect_url,Map<String, Object> model) {
//        try{
//            Response response = assistanPlatformClient.setToken("wangwei","sdfsdfsdfsdfs");
//            if (!response.getCode().equals(200) || response.getData() == null){
//                return response;
//            }
//            String userId = (String) response.getData();
//            return new Response((Object) userId);
//        }catch (Exception e){
//            logger.error(e.getMessage(),e);
//            return new ErrorResponse(e.getMessage());
//        }
//    }
}
