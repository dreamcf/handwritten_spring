package com.zhang.spring.config;

import com.zhang.spring.annotation.Component;
import com.zhang.spring.annotation.ComponentScan;
import com.zhang.spring.factory.BeanDefinitionFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class BeanDefinitionScan extends BeanDefinitionFactory {
    private Class configClass;
    private ClassLoader classLoader;

    public BeanDefinitionScan(Class configClass) throws IOException {
        this.configClass = configClass;
        classLoader = BeanDefinitionScan.class.getClassLoader();
        scan(configClass);
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
                getAllClass(file1);
            }
            String fileString = file1.toString();
            if (fileString.endsWith(".class")) {
                String filePath = fileString.substring(fileString.lastIndexOf("com"), fileString.lastIndexOf(".class")).replace("\\", ".");
                try {
                    Class<?> clazz = classLoader.loadClass(filePath);
                    if (clazz.isAnnotationPresent(Component.class)) {
                        registerBeanDefinition(clazz);
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
