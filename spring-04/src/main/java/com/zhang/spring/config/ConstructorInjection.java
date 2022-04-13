package com.zhang.spring.config;

import com.zhang.spring.bean.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * 有参构造接口
 */
public interface ConstructorInjection {
    Object constructor(String beanName, BeanDefinition beanDefinition, Constructor constructor,Object... args) throws Throwable;
}
