package com.zhang.spring.entry;

import com.zhang.spring.annotation.Autowired;
import com.zhang.spring.annotation.Component;
import com.zhang.spring.annotation.Scope;

@Scope
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserTest userTest;

    @Override
    public String todo() {
        return userTest.say();
    }
}
