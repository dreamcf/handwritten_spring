package com.zhang.spring.entry;

import com.zhang.spring.annotation.Scope;

@Scope
public class UserServiceImpl implements UserService {

    String id;

    public UserServiceImpl(String id) {
        this.id = id;
    }

    @Override
    public String todo() {
        return "客户" + id + "服务开启";
    }
}
