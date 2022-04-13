package com.zhang.spring.config;

import com.zhang.spring.bean.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

public class CglibConstructorInjection implements ConstructorInjection {
    @Override
    public Object constructor(String beanName, BeanDefinition beanDefinition, Constructor cor, Object... args) throws Throwable {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
        if (cor == null) return enhancer.create();
        return enhancer.create(cor.getParameterTypes(), args);
    }
}
