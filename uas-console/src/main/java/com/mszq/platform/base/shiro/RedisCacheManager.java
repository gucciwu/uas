package com.mszq.platform.base.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.mszq.platform.base.redis.RedisUtil;

/**
 * Shiro Cache 管理
 * 
 * @author shenxiangang
 *
 */
public class RedisCacheManager implements CacheManager {

    @Autowired
    private RedisUtil redisUtil;
//    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();
    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
//        Cache c = caches.get(name);
//        if (c == null) {
//            c = new RedisCache<K, V>();
//            caches.put(name, c);
//        }
//        return c;
        return new RedisCache<K, V>(redisUtil);
    }
}
