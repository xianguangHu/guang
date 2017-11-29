package com.guang.upms.client.shiro.filter;

import com.guang.common.base.baseConstants.UpmsConstant;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 重写authc过滤器
 * @author huxianguang
 * @create 2017-11-28-下午3:11
 **/
public class UpmsAuthenticationFilter extends AuthenticationFilter{


    /**
     * 表示是否允许访问
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        Session session = subject.getSession();
        String sessionId = session.getId().toString();
        System.out.println(sessionId);
        session.setAttribute(UpmsConstant.UPMS_TYPE,"client");
        System.out.println(subject);
        subject.isAuthenticated();
        return validateClient(request,response);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("onAccessDenide=====");
        return true;
    }

    /**
     * 认证中心登陆成功带回code
     * @param request
     * @param response
     * @return
     */
    private boolean validateClient(ServletRequest request,ServletResponse response) {


        return true;
    }
}
