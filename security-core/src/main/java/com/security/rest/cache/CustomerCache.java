package com.security.rest.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义缓存配置
 */
@Component
@Slf4j
public class CustomerCache implements MyCache{

    private MyCache myCache;

    public CustomerCache(){
        myCache = memoryMapCache();
    }

    public void userCustomerCache(){
        userCustomerCache(null);
    }

    public void userCustomerCache(String name){
        if(StringUtils.isEmpty(name)){
            myCache = memoryMapCache();
        }
    }

    @Override
    public void putObject(String key, Object value) {
        if(myCache == null){
            log.info("未选择自定义缓存处理器");
        }
        myCache.putObject(key, value);
    }

    @Override
    public <T> T getObject(String key, Class<T> tClass) {
        if(myCache == null){
            log.info("未选择自定义缓存处理器");
        }
        return myCache.getObject(key, tClass);
    }


    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public MemoryMapCache memoryMapCache(){
        return new MemoryMapCache();
    }

    /**
     * 用map作为暂时的缓存配置
     */
    private static class MemoryMapCache implements MyCache{
        private final static ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();


        @Override
        public void putObject(String key, Object value) {
            map.put(key, value);
        }

        @Override
        public <T> T getObject(String key, Class<T> tClass) {
            return tClass.cast(map.get(key));
        }
    }

}
