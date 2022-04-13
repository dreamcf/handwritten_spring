package com.zhang.spring.aop.config;


import java.lang.reflect.Method;

public class AdvisedSupport {
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

    public void setAspectClass(Class aspectClass) {
        AspectClass = aspectClass;
    }

    public Method getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(Method methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}
