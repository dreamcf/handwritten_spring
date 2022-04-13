package com.zhang.spring.entry;

import com.zhang.spring.annotation.Autowired;
import com.zhang.spring.annotation.Component;
import com.zhang.spring.annotation.Scope;
import com.zhang.spring.config.*;

@Scope
@Component
public class UserServiceImpl implements UserService, BeanNameAware , InitializingBean {


    @Autowired
    private UserTest userTest;

    @Autowired
    public   User user;
    @Override
    public String todo() {
        System.out.println("todo");
        return "todo";
    }

    @Override
    public void setBeanName(String var1) {
        System.out.println("Aware意识到");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化，我可以填充普通数据");
    }
}
