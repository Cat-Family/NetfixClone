package com.yill.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Component
@Log4j2
public class RedisUtils {


    /**
     * [redis]
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * [判断key是否存在]
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * [普通缓存获取]
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * [普通缓存删除]
     *
     * @param key 键
     * @return 值
     */
    public boolean delete(String key) {
        try {
            Boolean aBoolean = redisTemplate.hasKey(key);
            return aBoolean == false ? true : redisTemplate.delete(key);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * [普通缓存放入]
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * [普通缓存放入]
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean setWithTime(String key, Object value, int time) {
        try {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
