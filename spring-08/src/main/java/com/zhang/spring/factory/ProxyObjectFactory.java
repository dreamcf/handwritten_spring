package com.zhang.spring.factory;


import com.zhang.spring.aop.config.AdvisedSupport;
import com.zhang.spring.aop.config.CglibAopProxy;
import com.zhang.spring.aop.config.TargetSource;

import java.util.List;

public class ProxyObjectFactory {
    private List<AdvisedSupport> advisedSupportList;
    private Object bean;
    private String beanName;

    public Object getBean() {
        return bean;
    }

    public ProxyObjectFactory(String beanName, Object bean) {
        this.advisedSupportList = BeanDefinitionFactory.getAdvisedSupportList();
        this.bean = bean;
        this.beanName = beanName;
    }

    public Object getProxyObject() {
        for (AdvisedSupport advisedSupport : advisedSupportList) {
            if (advisedSupport.getMethodMatcher().matches(bean.getClass())) {
                bean = new CglibAopProxy(advisedSupport, new TargetSource(bean)).getAopProxy();
                return bean;
            }
        }
        return bean;
    }


}
