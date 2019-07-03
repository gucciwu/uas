package com.mszq.platform.base.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import com.mszq.platform.base.redis.RedisUtil;

/**
 * Shiro session 管理
 * 
 * @author shenxiangang
 *
 */
public class CustomShiroSessionDAO extends AbstractSessionDAO {

    public static final String REDIS_SHIRO_SESSION = "cms-shiro-session:";
    @Autowired
    private RedisUtil redisUtil;
    
    @Override
    public void update(Session session) throws UnknownSessionException {
        Long expireTime = session.getTimeout() / 1000;
        redisUtil.set(buildRedisSessionKey(session.getId()), session, expireTime);
    }

    @Override
    public void delete(Session session) {
        redisUtil.remove(buildRedisSessionKey(session.getId()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<Session>();
        Set<Serializable> keys = redisUtil.keys(REDIS_SHIRO_SESSION);
        if (keys != null && keys.size() > 0) {
            Object obj = null;
            for (Serializable key : keys) {
                obj = redisUtil.get(key);
                if (obj instanceof Session) {
                    sessions.add((Session)obj);
                }
            }
        }
        return sessions;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        Long expireTime = session.getTimeout() / 1000;
        redisUtil.set(buildRedisSessionKey(session.getId()), session, expireTime);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return (Session)redisUtil.get(buildRedisSessionKey(sessionId));
    }

    private String buildRedisSessionKey(Serializable sessionId) {
        return REDIS_SHIRO_SESSION + sessionId;
    }
}
