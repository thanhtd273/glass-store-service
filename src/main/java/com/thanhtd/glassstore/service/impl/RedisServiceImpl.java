package com.thanhtd.glassstore.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thanhtd.glassstore.core.utlis.RedisTemplateUtil;
import com.thanhtd.glassstore.service.RedisService;
import jodd.util.StringPool;
import jodd.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    private final RedisTemplateUtil<String> redisTemplateUtil;

    private final ObjectMapper jsonMapper;
    @Override
    public String createKey(String prefix, String key) {
        return redisTemplateUtil.createKey(prefix, key);
    }

    @Override
    public void saveList(String key, List<Object> objects, int expireIn) throws JsonProcessingException {
        String value = StringPool.EMPTY;
        if (!ObjectUtils.isEmpty(objects)) {
            value = jsonMapper.writeValueAsString(objects);
        }
        save(key, value, expireIn);
    }

    @Override
    public void saveObject(String key, Object object, int expireIn) throws JsonProcessingException {
        String value = StringPool.EMPTY;
        if (!ObjectUtils.isEmpty(object)) {
            value = jsonMapper.writeValueAsString(object);
        }
        save(key, value, expireIn);
    }

    @Override
    public void save(String key, String value, int expireIn) {
        redisTemplateUtil.setWithExpire(key, value, expireIn, TimeUnit.SECONDS);
    }

    @Override
    public String getValue(String key) {
        if (redisTemplateUtil.exist(key))
            return redisTemplateUtil.get(key);
        return null;
    }

    @Override
    public <T> Object getObject(String key, Class<T> tClass) {
        try {
            String value = getValue(key);
            if (StringUtil.isBlank(value)) return null;
            return jsonMapper.readValue(value, tClass);
        } catch (Exception e) {
            logger.error("Get object from redis fail, error: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public <T> List<T> getListObjects(String key, Class<T> tClass) {
        try {
            String value = getValue(key);
            if (StringUtil.isBlank(value))
                return null;
            return jsonMapper.readValue(value, jsonMapper.getTypeFactory().constructCollectionType(List.class, tClass));
        } catch (Exception e) {
            logger.info("Get list of objects from redis fail, error: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(String key) {
        if (redisTemplateUtil.exist(key))
            redisTemplateUtil.delete(key);
    }
}
