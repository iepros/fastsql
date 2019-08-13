package com.sunfrog.fastsql.aop;

import com.sunfrog.fastsql.common.util.ParamValidator;

/**
 * @author Leonard
 * @description
 * @date 2019/8/13 10:34
 */
public class Proxy {


    public  <T> T createProxy(Class<T> targetClazz){
        return createProxyFactory(targetClazz).getProxy(targetClazz);
    }

    private ProxyFactory createProxyFactory(Class targetClazz) {
        ParamValidator.validateObject(targetClazz);
        if(targetClazz.isInterface()){
            return new JdkProxyFactory();
        }
        return new CglibProxyFactory();
    }
}
