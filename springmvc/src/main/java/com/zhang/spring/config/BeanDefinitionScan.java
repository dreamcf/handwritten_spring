package com.zhang.spring.config;

import com.zhang.spring.annotation.*;
import com.zhang.spring.aop.config.AdvisedSupport;
import com.zhang.spring.aop.config.AspectJExpressionPointcut;
import com.zhang.spring.bean.BeanDefinition;
import com.zhang.spring.factory.BeanDefinitionFactory;
import com.zhang.spring.factory.SingletonBeanFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Map;

public class BeanDefinitionScan extends BeanDefinitionFactory {
    private Class configClass;
    private ClassLoader classLoader;

    public BeanDefinitionScan(Class configClass) throws Exception {
        this.configClass = configClass;
        classLoader = BeanDefinitionScan.class.getClassLoader();
        scan(configClass);
        for (Map.Entry<String, BeanDefinition> BeanDefinitionEntryMap : super.getBeanDefinitionMap().entrySet()) {
            if (BeanDefinitionEntryMap.getValue().getScope()!=null && BeanDefinitionEntryMap.getValue().getScope().equals("singleton") && !SingletonBeanFactory.hasSingletonMap(BeanDefinitionEntryMap.getKey())){
                createSingleton(BeanDefinitionEntryMap.getKey(),BeanDefinitionEntryMap.getValue());
            }
        }

    }

    private void scan(Class configClass) throws IOException {
        ComponentScan declaredAnnotation = (ComponentScan) configClass.getDeclaredAnnotation(ComponentScan.class);
        String value = declaredAnnotation.value();
        URL url = classLoader.getResource(value.replace(".", "/"));
        File file = new File(url.getFile());
        getAllClass(file);
    }

    private void getAllClass(File file) {
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isDirectory()) {
                System.out.println(file1);
                if(file1.toString().endsWith("aop") || file1.toString().endsWith("config")) continue;
                getAllClass(file1);
            }
            String fileString = file1.toString();
            if (fileString.endsWith(".class")) {
                String filePath = fileString.substring(fileString.lastIndexOf("com"), fileString.lastIndexOf(".class")).replace("\\", ".");
                try {
                    Class<?> clazz = classLoader.loadClass(filePath);
                    if (clazz.isAnnotationPresent(Component.class) || clazz.isAnnotationPresent(Configuration.class) || clazz.isAnnotationPresent(Controller.class)){
                        registerBeanDefinition(clazz);
                        //全局BeanPostProcess,存List
                        if(BeanPostProcessor.class.isAssignableFrom(clazz)){
                            BeanPostProcessor instance = (BeanPostProcessor) clazz.getDeclaredConstructor().newInstance();
                            addBeanPostProcessorList(instance);
                        }
                        //todo 切面类bean
                        if(clazz.isAnnotationPresent(Aspect.class)){

                            for (Method method : clazz.getDeclaredMethods()) {
                                if(method.isAnnotationPresent(Pointcut.class)){
                                    Pointcut pointcut = method.getDeclaredAnnotation(Pointcut.class);
                                    AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut(pointcut.value());
                                    for (Method clazzMethod : clazz.getDeclaredMethods()) {
                                        if(clazzMethod.isAnnotationPresent(Around.class)){
                                            String methodName = method.toString().substring(method.toString().lastIndexOf('.') + 1);
                                            if(methodName.equals(clazzMethod.getDeclaredAnnotation(Around.class).value())){
                                                addAdvisedSupportList(new AdvisedSupport(clazz,clazzMethod,aspectJExpressionPointcut));
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
