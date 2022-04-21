package com.zhang.spring.config;

import com.zhang.spring.bean.BeanDefinition;

import java.lang.reflect.Constructor;


public class DefaultConstructInjection implements  ConstructorInjection {
    @Override
    public Object constructor(String beanName, BeanDefinition beanDefinition, Constructor cor, Object...args) throws Throwable {
        Class beanClass = beanDefinition.getBeanClass();
        try {
            if(cor!=null){
                return beanClass.getDeclaredConstructor(cor.getParameterTypes()).newInstance(args);
            }else{
                return beanClass.getDeclaredConstructor().newInstance();
            }
        } catch (Exception e) {
            throw new Throwable(beanName,e);
        }
    }
}
