package com.zhang.spring.bean;

public interface BeanUtils {
    Object getBean(String beanName) throws IllegalAccessException, InstantiationException;
}
