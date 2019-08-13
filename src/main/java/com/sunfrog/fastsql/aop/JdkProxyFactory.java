package com.sunfrog.fastsql.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Leonard
 * @description
 * @date 2019/8/13 11:27
 */
public class JdkProxyFactory implements ProxyFactory, InvocationHandler {

    public <T> T getProxy(Class<T> targetClass) {
        Object o = Proxy.newProxyInstance(targetClass.getClassLoader(), new Class[]{targetClass}, this);
        return  (T)o;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        System.out.println("invoke start");

        System.out.println("invoke end");
        return null;
    }
}
