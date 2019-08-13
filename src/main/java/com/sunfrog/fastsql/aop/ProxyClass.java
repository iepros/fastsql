package com.sunfrog.fastsql.aop;

import java.util.Map;

/**
 * @author Leonard
 * @description
 * @date 2019/8/13 10:41
 */
public class ProxyClass {

    private Class targetClass;

    private Class proxyClass;

    private String name;

    private String pkg;

    private String sourceCode;

    private Map<String, byte[]> byteCode;


    public ProxyClass(){}
    public ProxyClass(Class targetClass){
        this.targetClass = targetClass;
        this.pkg = targetClass.getPackage().getName();
        this.name = targetClass.getSimpleName() + "$$EnhancerInternal";
    }
    public Class getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class targetClass) {
        this.targetClass = targetClass;
    }

    public Class getProxyClass() {
        return proxyClass;
    }

    public void setProxyClass(Class proxyClass) {
        this.proxyClass = proxyClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public boolean needProxy(){
        return true;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public Map<String, byte[]> getByteCode() {
        return byteCode;
    }

    public void setByteCode(Map<String, byte[]> byteCode) {
        this.byteCode = byteCode;
    }
}
