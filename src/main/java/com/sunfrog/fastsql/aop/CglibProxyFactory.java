package com.sunfrog.fastsql.aop;

import com.sunfrog.fastsql.common.util.ParamValidator;

import java.lang.reflect.Modifier;

/**
 * @author Leonard
 * @description
 * @date 2019/8/13 10:38
 */
public class CglibProxyFactory implements ProxyFactory {

    private static Compiler compiler;

    private static Generator generator;

    @Override
    public <T> T getProxy(Class<T> targetClass) {
        generator = new DefaultGenerator(targetClass);
        compiler = new JdkCompiler();
        Class proxyClass = getProxyClass(targetClass);
        ParamValidator.validateObject(proxyClass);
        try {
            T t = (T) proxyClass.newInstance();
            return t;
        } catch (Exception e) {
            return null;
        }
    }

    private <T> Class getProxyClass(Class<T> targetClass){
        int mod = targetClass.getModifiers();

        if(!Modifier.isPublic(mod)){
            throw new IllegalArgumentException("Only public class can be proxied");
        }
        if (Modifier.isFinal(mod)) {
            throw new IllegalArgumentException("final class can not be proxied");
        }
        if (Modifier.isAbstract(mod)) {
            throw new IllegalArgumentException("abstract class or interface can not be proxied");
        }
        ProxyClass proxyClass = generator.generate();
        ParamValidator.validateObject(proxyClass);
        if(proxyClass.needProxy()){
            compiler.compile(proxyClass);
            return proxyClass.getProxyClass();
        }else {
            return targetClass;
        }


    }
}
