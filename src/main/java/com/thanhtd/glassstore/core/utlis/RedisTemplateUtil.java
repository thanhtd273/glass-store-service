package com.thanhtd.glassstore.core.utlis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisTemplateUtil<T>{
    private final RedisTemplate<String, T> redisTemplate;

    private final ValueOperations<String, T> valueOperations;

    private final ListOperations<String, T> listOperations;

    private final SetOperations<String, T> setOperations;

    private HashOperations<String, String, T> hashOperations;

    @Autowired
    public RedisTemplateUtil(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
        this.listOperations = redisTemplate.opsForList();
        this.setOperations = redisTemplate.opsForSet();
    }

    public boolean exist(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public T get(String key) {
        return valueOperations.get(key);
    }

    public void set(String key, T value) {
        valueOperations.set(key, value);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void expire(String key, long timeout, TimeUnit timeUnit) {
        redisTemplate.expire(key, timeout, timeUnit);
    }

    public void addList(String key, T value) {
        listOperations.leftPush(key, value);
    }

    public List<T> getListMembers(String key) {
        return listOperations.range(key, 0, -1);
    }

    @SafeVarargs
    public final void addToSet(String key, T... values) {
        setOperations.add(key, values);
    }

    public Set<T> getSetMembers(String key) {
        return setOperations.members(key);
    }

    public T hget(String key, String field) {
        return hashOperations.get(key, field);
    }

    public void hset(String key, String field, T value) {
        hashOperations.put(key, field, value);
    }

    public void hdelete(String key, String field) {
        hashOperations.delete(key, field);
    }

    public String createKey(String prefix, String key) {
        return String.format("%s#%s", prefix, key);
    }

    public void setWithExpire(String key, T value, long timeout, TimeUnit timeUnit) {
        this.set(key, value);
        this.expire(key, timeout, timeUnit);
    }
}
