package com.guang.upms.client.shiro.session;

import com.guang.common.utils.RedisUtil;
import com.guang.common.utils.SerializableUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * 基于redis的sessionDao  实现共享缓存session
 * @author huxianguang
 * @create 2017-11-09-上午9:14
 **/
public class UpmsSessionDao extends CachingSessionDAO {

    private static Logger _log = LoggerFactory.getLogger(UpmsSessionDao.class);

    //会话key
    private final static String GUANG_UPMS_SHIRO_SESSION_ID = "guang-upms-shiro-session-id";

    @Override
    protected void doUpdate(Session session) {

    }

    @Override
    protected void doDelete(Session session) {

    }

    /**
     * 创建session 保存到数据库
     * @param session
     */
    @Override
    protected Serializable doCreate(Session session) {
        //生成新的session id
        Serializable sessionId = generateSessionId(session);
        //将生成的session id分配给session实例
        assignSessionId(session,sessionId);
        //将session保存到redis
        RedisUtil.set(GUANG_UPMS_SHIRO_SESSION_ID + "_" +sessionId,
                SerializableUtil.serializa(session), (int) (session.getTimeout()/1000));
        _log.debug("doCreate >>>>> sessionId={}",sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return null;
    }


}
