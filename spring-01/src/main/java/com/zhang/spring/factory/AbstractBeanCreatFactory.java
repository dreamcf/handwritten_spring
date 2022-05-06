package com.zhang.spring.factory;

import com.zhang.spring.bean.BeanDefinition;

public abstract class AbstractBeanCreatFactory extends AbstractBeanFactory {
    @Override
    protected Object create(String name, BeanDefinition beanDefinition) throws IllegalAccessException, InstantiationException {

        Object bean = beanDefinition.getBeanClass().newInstance();
        if (beanDefinition.getScope() != null && beanDefinition.getScope().equals("singleton") &&getSingletonBean(name) == null) {
            registerSingletonBean(name, bean);
            return getSingletonBean(name);
        }
        return bean;
    }
}
