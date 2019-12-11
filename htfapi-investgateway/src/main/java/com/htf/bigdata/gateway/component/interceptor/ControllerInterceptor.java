package com.htf.bigdata.gateway.component.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.htf.bigdata.gateway.config.code.BaseCodeEnum;
import com.htf.bigdata.gateway.model.Response;
import com.netflix.zuul.context.RequestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@ControllerAdvice
class ControllerInterceptor implements ErrorController {

    private static final Logger logger = LogManager.getLogger(ControllerInterceptor.class);

    @Override
    public String getErrorPath(){
        return "";
    }

    /**
     * 404 不能转发的url
     * @return
     */
    @RequestMapping("/error")
    public Response errorRequestUrlExceptionHanlder(HttpServletResponse response) {
        RequestContext ctx = RequestContext.getCurrentContext();
        String ctxBody = ctx.getResponseBody();
        response.setStatus(200);
        if (ctxBody != null && !ctxBody.isEmpty()) {
            try {
                return JSON.parseObject(ctxBody,Response.class);
            } catch (JSONException e) {

            } catch (Exception e) {

            }
        }
        return new Response(BaseCodeEnum.ERROR_REQUEST_URL.getCode(), BaseCodeEnum.ERROR_REQUEST_URL.getMessage());
    }

}