package com.sunfrog.fastsql.common.exception;

/**
 * @author Leonard
 * @description
 * @date 2019/8/13 11:04
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message){
        super(message);
    }
}
