package com.htf.bigdata.invest.indicatormanage.component.interceptor;

import com.htf.bigdata.invest.indicatormanage.component.context.HtfContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xuyali
 * @date 2019/6/13 13:54
 */
public class HtfInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String userId = httpServletRequest.getHeader("userId");
        if(StringUtils.isBlank(userId)){
            userId = httpServletRequest.getParameter("userId");
        }
        if(StringUtils.isNotBlank(userId)){
            HtfContext context = new HtfContext();
            context.setUserId(userId);
            HtfContext.htfContext.set(context);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
