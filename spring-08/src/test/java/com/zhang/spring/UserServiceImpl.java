package com.zhang.spring;

import com.zhang.spring.annotation.Autowired;
import com.zhang.spring.annotation.Component;
import com.zhang.spring.annotation.Scope;
import com.zhang.spring.entry.UserTest;

@Scope
@Component
public class UserServiceImpl implements UserService {


    @Autowired
    private UserTest userTest;



    public void setBeanName(String var1) {
        System.out.println("Aware意识到");
    }


    public String afterPropertiesSet() throws Exception {
        System.out.println("初始化，我可以填充普通数据");
        return  "sdofhdsf";
    }

    @Override
    public String todo() {
         return "sdffffffffffffff";
    }


}
