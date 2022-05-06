package com.zhang.spring.config;

import com.zhang.spring.annotation.Configuration;

@Configuration
public class MyBeanPostPrecess implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("前置处理器");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("后置处理器");
        return bean;
    }
}
