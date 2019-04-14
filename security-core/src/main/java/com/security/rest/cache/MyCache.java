package com.security.rest.cache;

public interface MyCache {
    void putObject(String key, Object value);
    <T> T getObject(String key, Class<T> tClass);
}
