package com.zhang.spring.factory;

import com.zhang.spring.annotation.Autowired;
import com.zhang.spring.bean.BeanDefinition;
import com.zhang.spring.config.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

public abstract class AbstractBeanCreatFactory extends AbstractBeanFactory {
    private CglibConstructorInjection cglibConstructorInjection = new CglibConstructorInjection();

    @Override
    protected Object createSingleton(String beanName, BeanDefinition beanDefinition) throws Exception {
        Object bean = beanDefinition.getBeanClass().newInstance();
        ProxyObjectFactory proxyObjectFactory = new ProxyObjectFactory(beanName, bean);
        Object finalBean = bean;
        bean = proxyObjectFactory.getProxyObject();
        registerSingletonFactories(beanName, proxyObjectFactory);
        //依赖注入
        diScan(beanName, finalBean);

        // Aware 回调
        if (bean instanceof BeanNameAware) {
            ((BeanNameAware) finalBean).setBeanName(beanDefinition.getBeanClass().getSimpleName());
        }
        List<BeanPostProcessor> beanPostProcessorList = getBeanPostProcessorList();
        // BeanPostProcessList前置处理器
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
            finalBean = beanPostProcessor.postProcessBeforeInitialization(finalBean, beanName);
        }
        // InitializeBean初始化
        if (bean instanceof InitializingBean) {
            ((InitializingBean) finalBean).afterPropertiesSet();
        }
        // BeanPostProcessList后置处理器
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
            finalBean = beanPostProcessor.postProcessAfterInitialization(finalBean, beanName);
        }
        removeEarlySingletonObjects(beanName);//删除二级缓存
        if (beanDefinition.getScope() != null && beanDefinition.getScope().equals("singleton")) {
            registerSingletonBean(beanName, bean);
        }
        return bean;
    }

    private void diScan(String beanName, Object bean) throws Exception {
        for (Field declaredField : bean.getClass().getDeclaredFields()) {
            if (declaredField.isAnnotationPresent(Autowired.class)) {
                String autowiredBeanName = declaredField.getType().toString();
                Object autowiredBean = getBean(autowiredBeanName.substring(autowiredBeanName.lastIndexOf(".") + 1));
                declaredField.setAccessible(true);
                declaredField.set(bean, autowiredBean);
                removeSingletonFactories(beanName);//删除三级缓存
                registerEarlySingletonObjects(beanName, bean);//放入二级缓存
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
