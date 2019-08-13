package com.sunfrog.fastsql.log;

/**
 * @author Leonard
 * @description
 * @date 2019/8/13 13:42
 */
public abstract class Log {

    private static ILogFactory logFactory;

    static{
        init();
    }

    static void init()  {
        try{
            Class<?> clazz = Class.forName("org.apache.log4j.Logger");
            Class<?> log4jLogFactoryClass = Class.forName("com.sunfrog.fastsql.log.Log4jFactory");
            logFactory = (ILogFactory)log4jLogFactoryClass.newInstance();
        }catch (Exception ex){
            logFactory = new JdkLogFactory();
        }
    }

    public static Log getLog(Class clazz){return logFactory.getLog(clazz);}

    public static Log getLog(String name){return logFactory.getLog(name);}


    public abstract void debug(String message);

    public abstract void info(String message);

    public abstract void warn(String message);

    public abstract void error(String message);

    public abstract void fatal(String message);

    public abstract boolean isDebugEnabled();

    public abstract boolean isInfoEnabled();

    public abstract boolean isWarnEnabled();

    public abstract boolean isErrorEnabled();

    public abstract boolean isFatalEnabled();
}
