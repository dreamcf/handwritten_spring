package com.zhang.spring.aop.config;

import java.lang.reflect.Method;

public interface MethodMatcher {
    boolean matches(Class<?> clazz);
    boolean matches(Method method, Class<?> targetClazz);
}
