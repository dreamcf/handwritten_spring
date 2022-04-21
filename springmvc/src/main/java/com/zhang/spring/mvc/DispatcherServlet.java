package com.zhang.spring.mvc;

import com.zhang.spring.App;
import com.zhang.spring.annotation.Controller;
import com.zhang.spring.annotation.RequestMapping;
import com.zhang.spring.config.BeanDefinitionScan;
import com.zhang.spring.factory.SingletonBeanFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
@WebServlet(urlPatterns = "/*",loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private Map<String,Method> urlMapping;
    private Map<String, Object> singletonMap;

    @Override
    public void init(){

            urlMapping=new HashMap<>();
        try {
            doMapping();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doSendRequest(req,resp);
    }

    private void doMapping() throws Exception {
        new BeanDefinitionScan(App.class);
       singletonMap = SingletonBeanFactory.getSingletonMap();
        if (singletonMap.isEmpty() || singletonMap.size() < 0) {
            return;
        }
        for (Map.Entry<String, Object> entry : singletonMap.entrySet()) {
            Class<?> obj = entry.getValue().getClass();
            if (!obj.isAnnotationPresent(Controller.class)) {
                continue;
            }
            String baseUrl = "";
            if (obj.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping rm = obj.getAnnotation(RequestMapping.class);
                baseUrl = rm.value();
            }
            Method[] methods = obj.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(RequestMapping.class)) {
                    continue;
                }
                RequestMapping anno = method.getAnnotation(RequestMapping.class);
                String url = getHandleUrl(baseUrl, anno);
                urlMapping.put(url, method);
                System.out.println("Mapping：" + url + "---Method：" + method);
            }
        }
    }

    private String getHandleUrl(String baseUrl, RequestMapping anno) {
        int length = baseUrl.length();
        String isEndSlash = baseUrl.substring(length - 1 , length);
        String handleUrl = "";
        if (!"/".equals(isEndSlash)){
            handleUrl = baseUrl.concat("/");
        }else {
            handleUrl = baseUrl;
        }
        String isFirstSlash = baseUrl.substring(0,1);
        if (!"/".equals(isFirstSlash)){
            handleUrl = "/" + handleUrl;
        }
        String value = anno.value();
        int length1 = value.length();
        String isExists = value.substring(0, 1);
        if ("/".equals(isExists)){
            value = value.substring(1,length1);
        }
        return handleUrl + value;
    }



    private void doSendRequest(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        String requestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        requestURI = requestURI.replace(contextPath, "").replace("/+", "/");      //得到请求url
        if (!urlMapping.containsKey(requestURI)) {
            resp.getWriter().write("404 NOT Found");
            return;
        }
        Method method = urlMapping.get(requestURI);
        String beanName = method.getDeclaringClass().getSimpleName();
        System.out.println("beanName：" + beanName);
        try {
            Object obj = method.invoke(singletonMap.get(beanName));
            resp.getOutputStream().write(obj.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
