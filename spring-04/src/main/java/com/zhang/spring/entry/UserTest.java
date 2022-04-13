package com.zhang.spring.entry;

import com.zhang.spring.annotation.Component;

@Component
public class UserTest {
    String say() {
        return "我已经被填充";
    }
}
