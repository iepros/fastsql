package com.sunfrog.fastsql.aop;

/**
 * @author Leonard
 * @description
 * @date 2019/8/13 10:35
 */
public interface ProxyFactory {

    <T> T getProxy(Class<T> targetClass);

}
