package com.sunfrog.fastsql.log;

/**
 * @author Leonard
 * @description
 * @date 2019/8/13 13:42
 */
public interface ILogFactory {

    Log getLog(Class<?> clazz);

    Log getLog(String name);


}
