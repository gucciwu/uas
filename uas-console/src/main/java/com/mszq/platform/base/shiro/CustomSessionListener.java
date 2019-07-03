package com.mszq.platform.base.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.mszq.platform.base.redis.RedisUtil;

/**
 * 重写Shiro Session 监听，session过期时，从redis中删除
 * 
 * @author shenxiangang
 *
 */
public class CustomSessionListener implements SessionListener {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 一个回话的生命周期开始
     */
    @Override
    public void onStart(Session session) {
        System.out.println("on start");
    }
    /**
     * 一个回话的生命周期结束
     */
    @Override
    public void onStop(Session session) {
        System.out.println("on stop");
    }

    @Override
    public void onExpiration(Session session) {
        redisUtil.remove(session.getId());
    }
}

