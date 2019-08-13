package com.sunfrog.fastsql.common.util;

/**
 * @author Leonard
 * @description
 * @date 2019/8/13 13:48
 */
public class StringUtil {

    public static boolean isEmpty(String s){
        if(s==null || s.length()==0){
            return false;
        }
        return true;
    }

    public static boolean isNotEmpty(String s){
        return !isEmpty(s);
    }
}
