package com.zhang.spring.entry;

import com.zhang.spring.annotation.*;
import com.zhang.spring.config.*;

@Scope
@Component
@PointClass
public class UserServiceImpl implements UserService, BeanNameAware, InitializingBean {


    @Autowired
    public UserTest userTest;

    @Autowired
    public User user;

    public String name;

    @Override
    public String todo() {
        return "hi,GoLang";
    }

    @Override
    public void setBeanName(String var1) {
        System.out.println("Aware意识到");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        name = "zcf";
        System.out.println("初始化，我可以填充普通数据");
    }
}
