package com.sunfrog.fastsql.log;

/**
 * @author Leonard
 * @description
 * @date 2019/8/13 13:46
 */
class Log4JLog extends Log {
    @Override
    public void debug(String message) {

    }
    @Override
    public void info(String message) {

    }
    @Override
    public void warn(String message) {

    }
    @Override
    public void error(String message) {

    }
    @Override
    public void fatal(String message) {

    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    @Override
    public boolean isFatalEnabled() {
        return false;
    }
}
