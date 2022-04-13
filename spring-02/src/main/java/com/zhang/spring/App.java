package com.zhang.spring;

import com.zhang.spring.entry.UserServiceImpl;

import com.zhang.spring.factory.BeanDefinitionFactory;

/**
 *@auther:dreamcf
 *@blog: www.zcfblog.top
 *@email:3307763375@qq.com
 */
public class App {
    public static void main(String[] args) throws Throwable {
        BeanDefinitionFactory beanDefinitionFactory = new BeanDefinitionFactory();
        beanDefinitionFactory.registerBeanDefinition(UserServiceImpl.class);
        UserServiceImpl userServiceImpl = (UserServiceImpl) beanDefinitionFactory.getBean("UserServiceImpl","1");
        UserServiceImpl userServiceImpl1 = (UserServiceImpl) beanDefinitionFactory.getBean("UserServiceImpl","1");
        System.out.println(userServiceImpl.equals(userServiceImpl1));
        System.out.println(userServiceImpl.todo());
    }
}
