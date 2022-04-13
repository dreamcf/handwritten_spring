package com.zhang.spring.aop;


import com.zhang.spring.annotation.*;
import org.aopalliance.intercept.MethodInvocation;

@Configuration
@Aspect
public class AopAspectTest {
    @Pointcut("execution (public * com.zhang.spring.entry.UserServiceImpl.*(..))")
    public void apiMethod(){};
    @Around("apiMethod()")
    public Object api(MethodInvocation invocation) throws Throwable {
        System.out.println("方法执行前");

        Object proceed = invocation.proceed();

        System.out.println("方法执行后");

        return proceed;
    }

}
