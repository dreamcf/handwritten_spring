package com.zhang.spring.config;

import com.zhang.spring.annotation.Configuration;
import com.zhang.spring.aop.config.AdvisedSupport;
import com.zhang.spring.aop.config.CglibAopProxy;
import com.zhang.spring.aop.config.TargetSource;
import com.zhang.spring.factory.BeanDefinitionFactory;

import java.util.List;

@Configuration
public class MyBeanPostPrecess implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        List<AdvisedSupport> advisedSupportList = BeanDefinitionFactory.getAdvisedSupportList();
        for (AdvisedSupport advisedSupport : advisedSupportList) {
            if(advisedSupport.getMethodMatcher().matches(bean.getClass())){
                bean = new CglibAopProxy(advisedSupport, new TargetSource(bean)).getAopProxy();
                return bean;
            }
        }
        return bean;
    }
}
