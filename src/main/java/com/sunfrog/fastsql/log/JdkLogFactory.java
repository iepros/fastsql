package com.sunfrog.fastsql.log;

import com.sunfrog.fastsql.common.util.ParamValidator;

import java.util.logging.Logger;

/**
 * @author Leonard
 * @description
 * @date 2019/8/13 13:43
 */
public class JdkLogFactory implements ILogFactory {


    public Log getLog(Class<?> clazz) {
        ParamValidator.validateObject(clazz);
        return new JdkLog(clazz);
    }

    public Log getLog(String name) {
        ParamValidator.validateString(name);
        return new JdkLog(name) ;
    }
}
