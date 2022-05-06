package com.zhang.spring.factory;


import com.zhang.spring.bean.BeanDefinition;
import com.zhang.spring.bean.BeanUtils;


public abstract class AbstractBeanFactory extends SingletonBeanFactory implements BeanUtils {
    @Override
    public Object getBean(String beanName) throws IllegalAccessException, InstantiationException {
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        if (beanDefinition.getScope() != null && beanDefinition.getScope().equals("singleton") && getSingletonBean(beanName) != null) {
            return getSingletonBean(beanName);
        }
        return create(beanName, beanDefinition);
    }

    protected abstract Object create(String name, BeanDefinition beanDefinition) throws IllegalAccessException, InstantiationException;

    protected abstract BeanDefinition getBeanDefinition(String name);

}
