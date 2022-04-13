package com.zhang.spring.entry;

import com.zhang.spring.annotation.Scope;

@Scope
public class UserServiceImpl implements UserService {
    @Override
    public String todo() {
        return "UserServiceImpl";
    }
}
