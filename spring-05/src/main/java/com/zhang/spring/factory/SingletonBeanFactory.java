package com.zhang.spring.factory;

import java.util.HashMap;

public class SingletonBeanFactory {
    private static HashMap<String, Object> singletonMap = new HashMap<>();
    private final HashMap<String, Object> earlySingletonObjects = new HashMap<>();

    void registerSingletonBean(String beanName, Object bean) throws IllegalAccessException, InstantiationException {
        singletonMap.put(beanName, bean);
    }

    void registerEarlySingletonObjects(String beanName, Object bean) throws IllegalAccessException, InstantiationException {
        earlySingletonObjects.put(beanName, bean);
    }

    Object getSingletonBean(String beanName) {
        Object o = singletonMap.get(beanName);
        return o;
    }

    public static boolean hasSingletonMap(String beanName) {
        return singletonMap.containsKey(beanName);
    }


    Object getEarlySingletonObjects(String beanName) {
        Object o = earlySingletonObjects.get(beanName);
        return o;
    }

    void removeEarlySingletonObjects(String beanName) {
        earlySingletonObjects.remove(beanName);
    }


    public Object hasSingletonCache(String beanName) {
        return singletonMap.getOrDefault(beanName, earlySingletonObjects.getOrDefault(beanName, null));
    }

}
