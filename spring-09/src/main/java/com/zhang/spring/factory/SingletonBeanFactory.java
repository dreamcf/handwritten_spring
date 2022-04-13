package com.zhang.spring.factory;


import java.util.HashMap;
import java.util.Map;

public class SingletonBeanFactory {
    //一级缓存
    private static Map<String, Object> singletonMap = new HashMap<>();
    //二级缓存
    private static Map<String, Object> earlySingletonObjects = new HashMap<>();
    //三级缓存
    private static Map<String, ProxyObjectFactory> singletonFactories = new HashMap<>();

    void registerSingletonBean(String beanName, Object bean) throws IllegalAccessException, InstantiationException {
        singletonMap.put(beanName, bean);
    }

    void registerEarlySingletonObjects(String beanName, Object bean) throws IllegalAccessException, InstantiationException {
        earlySingletonObjects.put(beanName, bean);
    }

    void registerSingletonFactories(String beanName, ProxyObjectFactory proxyObjectFactory) {
        singletonFactories.put(beanName, proxyObjectFactory);
    }

    Object getSingletonBean(String beanName) {
        Object o = singletonMap.get(beanName);
        return o;
    }

    public static boolean hasSingletonMap(String beanName) {
        return singletonMap.containsKey(beanName);
    }

    void removeEarlySingletonObjects(String beanName) {
        if (earlySingletonObjects.containsKey(beanName)) earlySingletonObjects.remove(beanName);
    }

    void removeSingletonFactories(String beanName) {
        if (singletonFactories.containsKey(beanName)) singletonFactories.remove(beanName);
    }

    public Object hasSingletonCache(String beanName) {
        return singletonMap.getOrDefault(beanName, earlySingletonObjects.getOrDefault(beanName, singletonFactories
                .getOrDefault(beanName, null)));
    }
}
