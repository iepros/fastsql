package com.sunfrog.fastsql.common.util;

import com.sunfrog.fastsql.common.exception.ValidationException;

/**
 * @author Leonard
 * @description
 * @date 2019/8/13 11:04
 */
public class ParamValidator {

     public static void validateObject(Object o){
         if(o==null){
             throw new ValidationException("object is null!");
         }
     }

     public static void validateString(String s){
         if(StringUtil.isEmpty(s)){
             throw new ValidationException("string is null!");
         }
     }
}
