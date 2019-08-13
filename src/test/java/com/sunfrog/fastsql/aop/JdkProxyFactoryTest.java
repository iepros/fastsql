package com.sunfrog.fastsql.aop;


import com.sunfrog.fastsql.log.Log;
import org.junit.Test;

public class JdkProxyFactoryTest {


    @Test
    public void testProxy(){
        TestInterface proxy = new Proxy().createProxy(TestImpl.class);
        Log log = Log.getLog(JdkProxyFactoryTest.class);
        log.error("haha");
        proxy.test();
    }
}