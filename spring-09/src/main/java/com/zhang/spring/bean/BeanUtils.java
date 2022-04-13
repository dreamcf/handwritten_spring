package com.zhang.spring.bean;

public interface BeanUtils {

    Object getBean(String beanName) throws Exception;

    Object getBean(String beanName, Object... args) throws Throwable;
}
