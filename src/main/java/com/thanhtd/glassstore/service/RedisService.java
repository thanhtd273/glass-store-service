package com.thanhtd.glassstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface RedisService {
    String createKey(String prefix, String key);
    void saveList(String key, List<Object> objects, int expireIn) throws JsonProcessingException;

    void saveObject(String key, Object object, int expireIn) throws JsonProcessingException;

    void save(String key, String value, int expireIn);

    String getValue(String key);

    <T> Object getObject(String key, Class<T> tClass);

    <T> List<T> getListObjects(String key, Class<T> tClass);

    void delete(String key);
}
