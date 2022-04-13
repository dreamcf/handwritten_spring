package com.zhang.spring.factory;


import com.zhang.spring.bean.BeanDefinition;
import com.zhang.spring.bean.BeanUtils;


public abstract class AbstractBeanFactory extends SingletonBeanFactory implements BeanUtils {
    @Override
    public Object getBean(String beanName) throws IllegalAccessException, InstantiationException {
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        if (beanDefinition == null) throw new NullPointerException();
        if (beanDefinition.getScope() != null && beanDefinition.getScope().equals("singleton") && getSingletonBean(beanName) != null)
            return getSingletonBean(beanName);
        return createSingleton(beanName, beanDefinition);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws Throwable {
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return create(beanName, beanDefinition, args);
    }

    protected abstract Object createSingleton(String name, BeanDefinition beanDefinition) throws IllegalAccessException, InstantiationException;

    protected abstract Object create(String name, BeanDefinition beanDefinition, Object... args) throws Throwable;

    protected abstract BeanDefinition getBeanDefinition(String name);

}
