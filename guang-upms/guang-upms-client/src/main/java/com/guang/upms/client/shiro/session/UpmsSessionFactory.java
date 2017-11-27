package com.guang.upms.client.shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;

import javax.servlet.http.HttpServletRequest;

/**
 * sessionFactory
 * @author huxianguang
 * @create 2017-11-26-下午4:55
 **/
public class UpmsSessionFactory implements SessionFactory{

    @Override
    public Session createSession(SessionContext initData) {
        UpmsSession session = new UpmsSession();
        //判断sessionContext是否是服务器
        if (null != initData && initData instanceof WebSessionContext) {
            WebSessionContext webSessionContext = (WebSessionContext) initData;
            HttpServletRequest request = (HttpServletRequest) webSessionContext.getServletRequest();
            if (null != request) {
                //将服务器session信息添加到shiro session中
                session.setHost(request.getRemoteAddr());
                session.setUserAgent(request.getHeader("User-Agent"));
            }
        }
        return session;
    }
}
