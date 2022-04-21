package com.zhang.spring.config;

import com.zhang.spring.annotation.Configuration;

@Configuration
public class MyBeanPostPrecess implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
//        List<AdvisedSupport> advisedSupportList = BeanDefinitionFactory.getAdvisedSupportList();
//        for (AdvisedSupport advisedSupport : advisedSupportList) {
//            if(advisedSupport.getMethodMatcher().matches(bean.getClass())){
//                bean = new Cglib2AopProxy(advisedSupport, new TargetSource(bean)).getAopProxy();
//                return bean;
//            }
//        }
        return bean;
    }
}
