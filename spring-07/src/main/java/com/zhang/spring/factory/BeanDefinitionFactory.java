package com.zhang.spring.factory;


import com.zhang.spring.aop.config.AdvisedSupport;
import com.zhang.spring.bean.BeanDefinition;

import com.zhang.spring.config.BeanPostProcessor;
import com.zhang.spring.annotation.Scope;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanDefinitionFactory extends AbstractBeanCreatFactory {
    private static Map<String, BeanDefinition> BeanDefinitionMap;
    // BeanPostProcessList
    private static List<BeanPostProcessor> BeanPostProcessorList;
    //todo AdvisedSupportList
    private static List<AdvisedSupport> AdvisedSupportList;

    public BeanDefinitionFactory() {
        BeanDefinitionMap = new HashMap<>();
        BeanPostProcessorList = new ArrayList<>();
        AdvisedSupportList = new ArrayList<>();
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
    //实现AbstractBeanFactory抽象类对于BeanPostProcessList的方法

    public List<BeanPostProcessor> getBeanPostProcessorList() {
        return BeanPostProcessorList;
    }

    public void addBeanPostProcessorList(BeanPostProcessor beanPostProcessor) {
        BeanPostProcessorList.add(beanPostProcessor);
    }

    public Map<String, BeanDefinition> getBeanDefinitionMap() {
        return BeanDefinitionMap;
    }

    public void addAdvisedSupportList(AdvisedSupport advisedSupport) {
        AdvisedSupportList.add(advisedSupport);
    }

    public static List<AdvisedSupport> getAdvisedSupportList() {
        return AdvisedSupportList;
    }
}
