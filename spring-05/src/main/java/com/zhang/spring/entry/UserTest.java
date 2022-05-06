package com.zhang.spring.entry;

import com.zhang.spring.annotation.Autowired;
import com.zhang.spring.annotation.Component;
import com.zhang.spring.annotation.Scope;

@Scope
@Component
public class UserTest {
    @Autowired
    private UserServiceImpl userService;
    String say(){
        return "我已经被填充";
    }
}
