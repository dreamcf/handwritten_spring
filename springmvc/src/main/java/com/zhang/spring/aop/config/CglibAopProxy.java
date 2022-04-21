package com.zhang.spring.aop.config;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibAopProxy implements AopProxy {


    private final AdvisedSupport advised;
    private final TargetSource targetSource;

    public CglibAopProxy(AdvisedSupport advised, TargetSource targetSource) {
        this.advised = advised;
        this.targetSource = targetSource;
    }

    @Override
    public Object getAopProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetSource.getTarget().getClass());
        enhancer.setInterfaces(targetSource.getTargetClass());
        enhancer.setCallback(new DynamicAdvisedInterceptor(advised, targetSource));
        return enhancer.create();
    }

    private static class DynamicAdvisedInterceptor implements MethodInterceptor {

        private final AdvisedSupport advised;

        private final TargetSource targetSource;

        public DynamicAdvisedInterceptor(AdvisedSupport advised, TargetSource targetSource) {
            this.advised = advised;
            this.targetSource = targetSource;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            CglibMethodInvocation methodInvocation = new CglibMethodInvocation(targetSource.getTarget(), method, objects, methodProxy);
            if (advised.getMethodMatcher().matches(method, targetSource.getTarget().getClass())) {
                return advised.getMethodInterceptor().invoke(advised.getAspectClass().newInstance(), methodInvocation);
            }
            return methodInvocation.proceed();
        }
    }

    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {

        private final MethodProxy methodProxy;

        public CglibMethodInvocation(Object target, Method method, Object[] arguments, MethodProxy methodProxy) {
            super(target, method, arguments);
            this.methodProxy = methodProxy;
        }

        @Override
        public Object proceed() throws Throwable {
            return this.methodProxy.invoke(this.target, this.arguments);
        }

    }

}
