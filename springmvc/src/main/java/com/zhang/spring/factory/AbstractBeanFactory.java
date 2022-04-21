package com.zhang.spring.factory;


import com.zhang.spring.bean.BeanDefinition;
import com.zhang.spring.bean.BeanUtils;
import com.zhang.spring.config.BeanPostProcessor;

import java.util.List;


public abstract class AbstractBeanFactory extends SingletonBeanFactory implements BeanUtils {
    @Override
    public Object getBean(String beanName) throws Exception {
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        if (beanDefinition == null) throw new NullPointerException();
        if (beanDefinition.getScope() != null && beanDefinition.getScope().equals("singleton") && hasSingletonCache(beanName) != null) {
            Object o = hasSingletonCache(beanName);
            if (o instanceof ProxyObjectFactory) {
                return ((ProxyObjectFactory) o).getBean();
            }
            return o;
        }
        return createSingleton(beanName, beanDefinition);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws Throwable {
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return create(beanName, beanDefinition, args);
    }


    protected abstract Object createSingleton(String name, BeanDefinition beanDefinition) throws Exception;

    protected abstract Object create(String name, BeanDefinition beanDefinition, Object... args) throws Throwable;

    protected abstract BeanDefinition getBeanDefinition(String name);

    public abstract List<BeanPostProcessor> getBeanPostProcessorList();

    public abstract void addBeanPostProcessorList(BeanPostProcessor beanPostProcessor);


}
