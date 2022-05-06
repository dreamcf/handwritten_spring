package com.zhang.spring.bean;

public interface BeanUtils {

    Object getBean(String beanName) throws IllegalAccessException, InstantiationException;

    Object getBean(String beanName, Object... args) throws Throwable;
}
