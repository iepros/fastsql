package com.sunfrog.fastsql.aop;

import com.sunfrog.fastsql.common.util.ClassLoaderUtil;
import com.sunfrog.fastsql.common.util.ClassUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Leonard
 * @description
 * @date 2019/8/13 15:19
 */
public abstract class AbstractCompiler implements Compiler {

    private static final Pattern PACKAGE_PATTERN = Pattern.compile("package\\s+([$_a-zA-Z][$_a-zA-Z0-9\\.]*);");

    private static final Pattern CLASS_PATTERN = Pattern.compile("class\\s+([$_a-zA-Z][$_a-zA-Z0-9]*)\\s+");

    @Override
    public void compile(ProxyClass proxyClass) {
        String code = proxyClass.getSourceCode();
        code = code.trim();
        Matcher matcher = PACKAGE_PATTERN.matcher(code);
        String pkg;
        if (matcher.find()) {
            pkg = matcher.group(1);
        } else {
            pkg = "";
        }
        matcher = CLASS_PATTERN.matcher(code);
        String cls;
        if (matcher.find()) {
            cls = matcher.group(1);
        } else {
            throw new IllegalArgumentException("No such class name in " + code);
        }
        String className = pkg != null && pkg.length() > 0 ? pkg + "." + cls : cls;
        try {
            Class<?> aClass = Class.forName(className, true, ClassLoaderUtil.findClassLoader(getClass()));
            proxyClass.setProxyClass(aClass);
        } catch (ClassNotFoundException e) {
            if (!code.endsWith("}")) {
                throw new IllegalStateException("The java code not endsWith \"}\", code: \n" + code + "\n");
            }
            try {
                Class<?> aClass = doCompile(className, code);
                proxyClass.setProxyClass(aClass);
            } catch (RuntimeException t) {
                throw t;
            } catch (Throwable t) {
                throw new IllegalStateException("Failed to compile class, cause: " + t.getMessage() + ", class: " + className + ", code: \n" + code + "\n, stack: ");
            }
        }
    }

    protected abstract Class<?> doCompile(String name, String source) throws Throwable;
}
