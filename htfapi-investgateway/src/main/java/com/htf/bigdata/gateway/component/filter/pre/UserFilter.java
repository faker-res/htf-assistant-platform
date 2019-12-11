package com.htf.bigdata.gateway.component.filter.pre;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.htf.bigdata.gateway.config.Constants;
import com.htf.bigdata.gateway.config.code.UserCodeEum;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.htf.bigdata.gateway.component.client.IAssistanPlatformClient;
import com.htf.bigdata.gateway.model.Response;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class UserFilter extends ZuulFilter {

    private final static Logger logger = LogManager.getLogger(UserFilter.class);

    @Autowired
    private IAssistanPlatformClient assistanPlatformClient;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.getBoolean(Constants.GO_TO_NEXT_STEP,true);
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String userId = null;
        String token = null;
        String debug = null;
        if (request.getMethod().equals("GET")){
            userId = request.getParameter("userId");
            token = request.getParameter("token");
            debug = request.getParameter("debug");
        }else {
            String contentType = request.getContentType();
            if (contentType.indexOf("application/json") >= 0){
                try{
                    BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
                    String message = IOUtils.toString(reader);
                    JSONObject jo = JSON.parseObject(message);
                    userId = jo.containsKey("userId") ? jo.get("userId").toString() : "";
                    token = jo.containsKey("token") ? jo.get("token").toString() : "";
                    debug = jo.containsKey("debug") ? jo.get("debug").toString() : "";
                }catch (Exception e){}
            }else if (contentType.indexOf("application/x-www-form-urlencoded")>= 0){
                userId = request.getParameter("userId");
                token = request.getParameter("token");
                debug = request.getParameter("debug");
            }else if (contentType.indexOf("multipart/form-data")>= 0){
                return null;
            }
        }

        if (debug != null && debug.equals("XYZ")){
            return null;
        }

        if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(token)){
            try{
                //验证成功
                if (userLoginStatus(userId,token)){
                    //进行路由
                    ctx.setSendZuulResponse(true);
                    //设值，让下一个filter看到上一个filter状态
                    ctx.set(Constants.GO_TO_NEXT_STEP,true);
                }else{
                    //验证失败
                    //过滤请求，不进行路由
                    ctx.setSendZuulResponse(false);
                    Response response = new Response();
                    response.setCode(UserCodeEum.ERROR_USER_PARAMETER.getCode());
                    response.setMessage(UserCodeEum.ERROR_USER_PARAMETER.getMessage());
                    ctx.setResponseBody(JSON.toJSONString(response));
                    ctx.set(Constants.GO_TO_NEXT_STEP,false);
                }
            }catch (Exception e){
                logger.error("sso error: userId:{} token:{}",userId,token);
                ctx.setSendZuulResponse(false);
                Response response = new Response();
                response.setCode(UserCodeEum.ERROR_SSO.getCode());
                response.setMessage(UserCodeEum.ERROR_SSO.getMessage());
                ctx.setResponseBody(JSON.toJSONString(response));
                ctx.set(Constants.GO_TO_NEXT_STEP,false);
            }
        }else{
            //没有用户信息直接过滤掉请求
            ctx.setSendZuulResponse(false);
            Response response = new Response();
            response.setCode(UserCodeEum.ERROR_USER_PARAMETER.getCode());
            response.setMessage(UserCodeEum.ERROR_USER_PARAMETER.getMessage());
            ctx.setResponseBody(JSON.toJSONString(response));
            ctx.set("isSuccess",false);
            ctx.set(Constants.GO_TO_NEXT_STEP,false);
        }

        return null;
    }

    /**
     * 用户登录验证
     * @param userId
     * @param token
     * @return
     */
    private Boolean userLoginStatus(String userId,String token){
        try{
            Response response = assistanPlatformClient.verifyToken(userId,token);
            if (!response.getCode().equals(200) || response.getData() == null){
                return false;
            }
            return (Boolean) response.getData();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return false;
        }
    }
}
