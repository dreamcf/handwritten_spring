package com.zhang.spring.aop;


import com.zhang.spring.annotation.Around;
import com.zhang.spring.annotation.Aspect;
import com.zhang.spring.annotation.Component;
import com.zhang.spring.annotation.Pointcut;
import org.aopalliance.intercept.MethodInvocation;

@Component
@Aspect
public class AopAspectTest {
    @Pointcut("execution (public * com.zhang.spring.entry.UserServiceImpl.*(..))")
    public void apiMethod(){};

    @Around("apiMethod()")
    public Object api(MethodInvocation invocation) throws Throwable {
        System.out.println("方法执行前");

        Object proceed;
        try {
            return  invocation.proceed();


        } finally {
            System.out.println("方法执行后");
        }


    }

}
