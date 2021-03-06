package com.zhang.spring;

import com.zhang.spring.annotation.ComponentScan;
import com.zhang.spring.entry.User;
import com.zhang.spring.entry.UserServiceImpl;

import com.zhang.spring.entry.UserTest;
import com.zhang.spring.config.BeanDefinitionScan;
import com.zhang.spring.factory.BeanDefinitionFactory;

/**
 *@auther:dreamcf
 *@blog: www.zcfblog.top
 *@email:3307763375@qq.com
 */
@ComponentScan("com.zhang.spring")
public class App {
    public static void main(String[] args) throws Throwable {
        BeanDefinitionFactory beanDefinitionFactory = new BeanDefinitionScan(App.class);
        UserServiceImpl userServiceImpl = (UserServiceImpl) beanDefinitionFactory.getBean("UserServiceImpl");
        UserTest userTest = (UserTest) beanDefinitionFactory.getBean("UserTest");
        User users = (User) beanDefinitionFactory.getBean("User");
        userServiceImpl.todo();
        userTest.test();
    }
}
