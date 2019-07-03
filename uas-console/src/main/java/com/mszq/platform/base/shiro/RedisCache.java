package com.mszq.platform.base.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import com.mszq.platform.base.redis.RedisUtil;

/**
 * Shiro Cache 管理
 * 
 * @author shenxiangang
 *
 * @param <K>
 * @param <V>
 */
public class RedisCache<K,V> implements Cache<K,V> {

    private static final String REDIS_SHIRO_CACHE = "redis-shiro-cache:";
    private RedisUtil redisUtil;

    public RedisCache(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public void clear() throws CacheException {
        redisUtil.removePattern(REDIS_SHIRO_CACHE);
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(K key) throws CacheException {
        if (key == null) {
            return null;
        }
        return (V)redisUtil.get(buildCacheKey(key));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<K> keys() {
        Set<Serializable> keys = redisUtil.keys(REDIS_SHIRO_CACHE);
        Set<K> result = new HashSet<K>();
        if (keys != null && keys.size() != 0) {
            for (Serializable key : keys) {
                result.add((K)key);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V put(K key, V value) throws CacheException {
        if (key == null) {
            return null;
        }
        V obj = (V)redisUtil.get(buildCacheKey(key));
        redisUtil.set(buildCacheKey(key), value);
        return obj;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V remove(K key) throws CacheException {
        if (key == null) {
            return null;
        }
        V obj = (V)redisUtil.get(buildCacheKey(key));
        redisUtil.remove(buildCacheKey(key));
        return obj;
    }

    @Override
    public int size() {
        Set<Serializable> keys = redisUtil.keys(REDIS_SHIRO_CACHE);
        return keys != null ? keys.size() : 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<V> values() {
        Set<Serializable> keys = redisUtil.keys(REDIS_SHIRO_CACHE);
        Set<V> result = new HashSet<V>();
        if (keys != null && keys.size() > 0) {
            for (Serializable key : keys) {
                result.add((V)redisUtil.get(key));
            }
        }
        return result;
    }

    private String buildCacheKey(K key) {
        return REDIS_SHIRO_CACHE + key;
    }
}
