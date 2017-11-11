package com.guang.upms.server.interceptor;

import com.guang.upms.dao.model.UpmsUser;
import com.guang.upms.rpc.api.UpmsApiService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huxianguang
 * @create 2017-11-11-下午7:27
 **/
public class UpmsInterceptor extends HandlerInterceptorAdapter{

    @Autowired
    private UpmsApiService upmsApiService;

    /**
     * 该方法在请求处理之前调用，只有返回true才会继续执行后续Interceptor
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //过滤ajax
        /**
         * Ajax 请求比传统请求的协议头（header）多了个“x-requested-with  XMLHttpRequest
         * 可以利用它，request.getHeader("x-requested-with"); 为 null，则为传统同步请求，为 XMLHttpRequest，则为 Ajax 异步请求
         */
        if (null != request.getHeader("X-Requested-With") && request.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        }
        //登陆信息
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        UpmsUser user = upmsApiService.selectUpmsUserByUsername(username);
        request.setAttribute("upmsUser",user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
