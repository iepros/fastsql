package com.sunfrog.fastsql.log;


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Leonard
 * @description
 * @date 2019/8/13 13:45
 */
class JdkLog extends Log {

    private java.util.logging.Logger log;

    private String name;

    public JdkLog(Class clazz) {
        this(clazz.getName());
    }

    public JdkLog(String name) {
        this.name = name;
        log = Logger.getLogger(name);
    }

    @Override
    public void debug(String message) {
        log(Level.FINE, message);
    }

    @Override
    public void info(String message) {
        log(Level.INFO, message);
    }

    @Override

    public void warn(String message) {
        log(Level.WARNING, message);
    }

    @Override
    public void error(String message) {
        log(Level.SEVERE, message);
    }

    @Override
    public void fatal(String message) {
        log(Level.SEVERE, message);
    }

    @Override
    public boolean isDebugEnabled() {
        return log.isLoggable(Level.FINE);
    }

    @Override
    public boolean isInfoEnabled() {
        return log.isLoggable(Level.INFO);

    }

    @Override
    public boolean isWarnEnabled() {
        return log.isLoggable(Level.WARNING);

    }

    @Override
    public boolean isErrorEnabled() {
        return log.isLoggable(Level.SEVERE);

    }

    @Override
    public boolean isFatalEnabled() {
        return log.isLoggable(Level.SEVERE);
    }

    private void log(Level level, String message) {
        log.logp(level, name, Thread.currentThread().getStackTrace()[1].getMethodName(), message);
    }
}
