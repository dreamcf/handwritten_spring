package com.zhang.spring.factory;


import com.zhang.spring.bean.BeanDefinition;

import com.zhang.spring.annotation.Scope;


import java.util.HashMap;
import java.util.Map;

public class BeanDefinitionFactory extends AbstractBeanCreatFactory {
    private final Map<String, BeanDefinition> BeanDefinitionMap;

    public BeanDefinitionFactory() {
        BeanDefinitionMap = new HashMap<>();
    }

    public void registerBeanDefinition(Class beanClass) throws InstantiationException, IllegalAccessException {
        if (!BeanDefinitionMap.containsKey(beanClass.getName())) {
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setBeanClass(beanClass);
            if (beanClass.isAnnotationPresent(Scope.class)) {
                Scope annotation = (Scope) beanClass.getDeclaredAnnotation(Scope.class);
                beanDefinition.setScope(annotation.value());
            }
            BeanDefinitionMap.put(beanClass.getSimpleName(), beanDefinition);
        }
    }

    public BeanDefinition getBeanDefinition(String beanName) {
        return BeanDefinitionMap.get(beanName);
    }

    public Map<String, BeanDefinition> getBeanDefinitionMap() {
        return BeanDefinitionMap;
    }
}
