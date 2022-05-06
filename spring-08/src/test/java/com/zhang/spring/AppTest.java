package com.zhang.spring;

import static org.junit.Assert.assertTrue;

import com.zhang.spring.AOP.*;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws Exception {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* com.zhang.spring.UserServiceImpl.*(..))");
        Class<UserServiceImpl> clazz = UserServiceImpl.class;
        Method method = clazz.getDeclaredMethod("todo");
//        System.out.println(pointcut.matches(clazz));
        System.out.println(pointcut.matches(method, clazz));


        UserServiceImpl userService = new UserServiceImpl();

        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* com.zhang.spring.UserServiceImpl.todo(..))"));
        UserServiceImpl aopProxy = (UserServiceImpl) new Cglib2AopProxy(advisedSupport).getAopProxy();
//        UserService aopProxy = (UserService) new JdkDynamicAopProxy(advisedSupport).getAopProxy();
        System.out.println(aopProxy.todo());
    }

    @Test
    public void t(){
        test1();
    }
    public Object test1(){
        try {
            return test2();
        } finally {
            System.out.println("finally");
        }
    }
    public  Object test2(){
        System.out.println("test2");
        UserServiceImpl userService = new UserServiceImpl();
        return userService;
    }
}
