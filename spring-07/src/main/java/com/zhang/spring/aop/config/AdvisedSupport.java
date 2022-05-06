package com.zhang.spring.aop.config;


import java.lang.reflect.Method;

public class AdvisedSupport {
    //切面类
    private Class AspectClass;
    // 方法拦截器
    private Method methodInterceptor;
    // 方法匹配器(检查目标方法是否符合通知条件)
    private MethodMatcher methodMatcher;

    public AdvisedSupport(Class aspectClass, Method methodInterceptor, MethodMatcher methodMatcher) {
        AspectClass = aspectClass;
        this.methodInterceptor = methodInterceptor;
        this.methodMatcher = methodMatcher;
    }

    public Class getAspectClass() {
        return AspectClass;
    }


    public Method getMethodInterceptor() {
        return methodInterceptor;
    }


    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

}
