package com.zhang.spring.factory;

import java.util.HashMap;

public class SingletonBeanFactory {
    private static HashMap<String, Object> singletonMap = new HashMap<>();

    public void registerSingletonBean(String beanName, Object bean) throws IllegalAccessException, InstantiationException {
        singletonMap.put(beanName, bean);
    }

    public static boolean hasSingletonMap(String beanName) {
        return singletonMap.containsKey(beanName);
    }


    public Object getSingletonBean(String beanName) {
        Object o = singletonMap.get(beanName);
        return o;
    }


}
