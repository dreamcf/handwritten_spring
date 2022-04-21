package com.zhang.spring.Controller;

import com.zhang.spring.annotation.Autowired;
import com.zhang.spring.annotation.Controller;
import com.zhang.spring.annotation.RequestMapping;
import com.zhang.spring.annotation.Scope;
import com.zhang.spring.entry.UserService;
import com.zhang.spring.entry.UserServiceImpl;

@Controller
@RequestMapping("user")
@Scope
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @RequestMapping("todo/todo")
    public String queryUser(){
        return  userService.todo();
    }

    @RequestMapping("/index")
    public String queryIndex(){
        return  "index.jsp";
    }
}
