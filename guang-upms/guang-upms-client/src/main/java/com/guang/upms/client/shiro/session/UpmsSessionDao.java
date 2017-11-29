package com.guang.upms.client.shiro.session;

import com.guang.common.utils.RedisUtil;
import com.guang.common.utils.SerializableUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.*;

/**
 * 基于redis的sessionDao  实现共享缓存session
 * @author huxianguang
 * @create 2017-11-09-上午9:14
 **/
public class UpmsSessionDao extends CachingSessionDAO {

    private static Logger _log = LoggerFactory.getLogger(UpmsSessionDao.class);

    //会话key
    private final static String GUANG_UPMS_SHIRO_SESSION_ID = "guang-upms-shiro-session-id";

    //全局会话key列表
    private final static String GUANG_UPMS_SERVER_SESSION_IDS = "guang-upms-server-session-ids";
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
        _log.info("doCreate >>>>> sessionId={}",sessionId);
        return sessionId;
    }

    /**
     * 查询session
     * @param sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        String session = RedisUtil.get(GUANG_UPMS_SHIRO_SESSION_ID + "_" + sessionId);
        _log.info("doReadSession >>>>> sessionId={}",session);
        Session deserialize = SerializableUtil.deserialize(session);
        System.out.println();
        return deserialize;
    }



    public Map getActiveSessions(int offset,int limit) {
        Map sessions = new HashMap();
        Jedis jedis = RedisUtil.getJedis();
        //获取在线session人数
        Long total = jedis.llen(GUANG_UPMS_SERVER_SESSION_IDS);
        List<String> ids = jedis.lrange(GUANG_UPMS_SERVER_SESSION_IDS, offset, (offset + limit - 1));
        List<Session> rows = new ArrayList<Session>();
        for (String id : ids) {
//            RedisUtil.get();
        }
        return sessions;
    }

    public void updateStatus(Serializable sessionId,UpmsSession.OnlineStatus onlineStatus){
        UpmsSession session = (UpmsSession) doReadSession(sessionId);
        if (null == session) {
            return;
        }
        session.setStatus(onlineStatus);
        RedisUtil.set(GUANG_UPMS_SHIRO_SESSION_ID + "_" + session.getId(), SerializableUtil.serializa(session), (int) session.getTimeout() / 1000);
    }


    @Override
    protected void doUpdate(Session session) {
// 如果会话过期/停止 没必要再更新了
        if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) {
            return;
        }
        // 更新session的最后一次访问时间
        UpmsSession upmsSession = (UpmsSession) session;
        UpmsSession cacheUpmsSession = (UpmsSession) doReadSession(session.getId());
        if (null != cacheUpmsSession) {
            upmsSession.setStatus(cacheUpmsSession.getStatus());
            upmsSession.setAttribute("FORCE_LOGOUT", cacheUpmsSession.getAttribute("FORCE_LOGOUT"));
        }
        RedisUtil.set(GUANG_UPMS_SHIRO_SESSION_ID + "_" + session.getId(), SerializableUtil.serializa(session), (int) session.getTimeout() / 1000);
        // 更新ZHENG_UPMS_SERVER_SESSION_ID、ZHENG_UPMS_SERVER_CODE过期时间 TODO
        _log.debug("doUpdate >>>>> sessionId={}", session.getId());
    }

    @Override
    protected void doDelete(Session session) {

    }

    @Override
    public Collection<Session> getActiveSessions() {
        return null;
    }
}
