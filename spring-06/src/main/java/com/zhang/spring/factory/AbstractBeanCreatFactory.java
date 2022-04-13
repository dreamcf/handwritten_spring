package com.zhang.spring.factory;

import com.zhang.spring.bean.BeanDefinition;
import com.zhang.spring.annotation.Autowired;
import com.zhang.spring.config.CglibConstructorInjection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public abstract class AbstractBeanCreatFactory extends AbstractBeanFactory {
    private CglibConstructorInjection cglibConstructorInjection = new CglibConstructorInjection();

    @Override
    protected Object createSingleton(String beanName, BeanDefinition beanDefinition) throws IllegalAccessException, InstantiationException {
        Object bean = beanDefinition.getBeanClass().newInstance();
        diScan(beanName, bean);
        if (beanDefinition.getScope() != null && beanDefinition.getScope().equals("singleton")) {
            registerSingletonBean(beanName, bean);
        }
        return bean;
    }

    private void diScan(String beanName, Object bean) throws IllegalAccessException, InstantiationException {
        for (Field declaredField : bean.getClass().getDeclaredFields()) {
            if (declaredField.isAnnotationPresent(Autowired.class)) {
                registerEarlySingletonObjects(beanName, bean);//放入二级缓存
                String autowiredBeanName = declaredField.getType().toString();
                Object autowiredBean = getBean(autowiredBeanName.substring(autowiredBeanName.lastIndexOf(".") + 1));
                declaredField.setAccessible(true);
                declaredField.set(bean, autowiredBean);
                removeEarlySingletonObjects(beanName);//删除二级缓存
            }
        }
    }

    @Override
    protected Object create(String beanName, BeanDefinition beanDefinition, Object... args) throws Throwable {
        if (beanDefinition.getScope().equals("singleton") && getSingletonBean(beanName) != null)
            return getSingletonBean(beanName);
        Constructor cor = null;
        Class beanClass = beanDefinition.getBeanClass();
        Constructor[] constructors = beanClass.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            if (args != null && constructor.getParameterTypes().length == args.length) {
                cor = constructor;
                break;
            }
        }
        Object bean = cglibConstructorInjection.constructor(beanName, beanDefinition, cor, args);
        if (beanDefinition.getScope() != null && beanDefinition.getScope().equals("singleton"))
            registerSingletonBean(beanName, bean);
        return bean;
    }
}
