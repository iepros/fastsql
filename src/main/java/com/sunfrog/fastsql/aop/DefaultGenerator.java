package com.sunfrog.fastsql.aop;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.ProxyLoader;
import com.sunfrog.fastsql.log.Log;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Leonard
 * @description
 * @date 2019/8/13 11:02
 */
public class DefaultGenerator implements Generator {

    private ProxyLoader proxyLoader;

    private ProxyClass proxyClass;

    private static final String CODE_PACKAGE = "package %s;\n";

    private static final String CODE_IMPORTS = "import %s;\n";

    private static final String CODE_CLASS_DECLARATION = "public class %s extends %s {\n";

    private static final String CODE_METHOD_DECLARATION = "public %s %s(%s) %s {\n%s}\n";

    private static final String CODE_METHOD_ARGUMENT = "%s arg%d";

    private static final String CODE_METHOD_THROWS = "throws %s";

    private static final String CODE_METHOD_CONTENT =
            "System.out.println(\"代理成功!\");"
            ;


    public DefaultGenerator(Class<?> targetClass){
        this.proxyClass = new ProxyClass(targetClass);
    }

    private static final Log log = Log.getLog(DefaultGenerator.class);

    @Override
    public ProxyClass generate() {
        StringBuilder code = new StringBuilder();
        code.append(generatePackageInfo());
        code.append(generateClassDeclaration());
        Method[] methods = proxyClass.getTargetClass().getMethods();
        for (Method method : methods) {
            if(!isSkipMethod(method)) {
                code.append(generateMethod(method));
            }
        }
        code.append("}");
        if(log.isInfoEnabled()){
            log.info("sourceCode is "+code.toString());
        }
        proxyClass.setSourceCode(code.toString());
        return proxyClass;
    }

    public String generatePackageInfo(){
        String packageInfo = String.format(CODE_PACKAGE, proxyClass.getTargetClass().getPackage().getName());
        return packageInfo;
    }

    public String generateImportInfo(String importName){
        return String.format(CODE_IMPORTS, importName);
    }

    private String generateClassDeclaration() {
        TypeVariable[] typeParameters = proxyClass.getTargetClass().getTypeParameters();
        return String.format(CODE_CLASS_DECLARATION, proxyClass.getName()+getTypeVars(typeParameters), getTargetName()+getTargetTypeVars(typeParameters));
    }

    private String generateMethod(Method method) {
        String methodReturnType = method.getReturnType().getCanonicalName();
        String methodName = method.getName();
        String methodContent = generateMethodContent(method);
        String methodArgs = generateMethodArguments(method);
        String methodThrows = generateMethodThrows(method);
        return String.format(CODE_METHOD_DECLARATION, methodReturnType, methodName, methodArgs, methodThrows, methodContent);
    }


    /**
     * generate method arguments
     */
    private String generateMethodArguments(Method method) {
        Class<?>[] pts = method.getParameterTypes();
        return IntStream.range(0, pts.length)
                .mapToObj(i -> String.format(CODE_METHOD_ARGUMENT, pts[i].getCanonicalName(), i))
                .collect(Collectors.joining(", "));
    }

    protected boolean isSkipMethod(Method method) {
        int mod = method.getModifiers();
        if ( ! Modifier.isPublic(mod) ) {
            return true;
        }

        if (Modifier.isFinal(mod) || Modifier.isStatic(mod) || Modifier.isAbstract(mod)) {
            return true;
        }

        String n = method.getName();
        if (n.equals("toString") || n.equals("hashCode") || n.equals("equals")) {
            return true;
        }

        return false;
    }
    /**
     * generate method throws
     */
    private String generateMethodThrows(Method method) {
        Class<?>[] ets = method.getExceptionTypes();
        if (ets.length > 0) {
            String list = Arrays.stream(ets).map(Class::getCanonicalName).collect(Collectors.joining(", "));
            return String.format(CODE_METHOD_THROWS, list);
        } else {
            return "";
        }
    }

    /**
     * generate method content
     */
    private String generateMethodContent(Method method) {
        return String.format(CODE_METHOD_CONTENT,method.getReturnType(),method.getName());
    }

    /**
     * 支持对 static 类的代理
     */
    protected String getTargetName() {
        Class target = proxyClass.getTargetClass();
        if (Modifier.isStatic(target.getModifiers())) {
            // 无法兼容主类类名中包含字符 '$'，例如：com.xxx.My$Target&Inner
            // return target.getName().replace('$', '.');

            // 静态类的 getName() 值为 com.xxx.Target&Inner 需要将字符 '$' 替换成 '.'
            String ret = target.getName();
            int index = ret.lastIndexOf('$');
            return ret.substring(0, index) + "." + ret.substring(index + 1);
        } else {
            return target.getSimpleName();
        }
    }

    /**
     * 获取子类泛型变量，也可用于获取方法泛型变量
     */
    @SuppressWarnings("rawtypes")
    protected String getTypeVars(TypeVariable[] typeVars) {
        if (typeVars == null|| typeVars.length == 0) {
            return "";
        }

        StringBuilder ret = new StringBuilder();

        ret.append('<');
        for (int i=0; i<typeVars.length; i++) {
            TypeVariable tv = typeVars[i];
            if (i > 0) {
                ret.append(", ");
            }

            ret.append(tv.getName());

            // T extends Map & List & Set
            Type[] bounds = tv.getBounds();
            if (bounds.length == 1) {
                if (bounds[0] != Object.class) {
                    ret.append(" extends ").append(bounds[0].getTypeName());
                    continue ;
                }
            } else {
                for (int j=0; j<bounds.length; j++) {
                    String tn = bounds[j].getTypeName();
                    if (j > 0) {
                        ret.append(" & ").append(tn);
                    } else {
                        ret.append(" extends ").append(tn);
                    }
                }
            }
        }

        return ret.append('>').toString();
    }

    /**
     * 获取父类泛型变量
     *
     * 相对于 getTypeVars(...) 取消了 TypeVariable.getBounds() 内容的生成，否则编译错误
     */
    @SuppressWarnings("rawtypes")
    protected String getTargetTypeVars(TypeVariable[] typeVars) {
        if (typeVars == null|| typeVars.length == 0) {
            return "";
        }

        StringBuilder ret = new StringBuilder();
        ret.append('<');
        for (int i=0; i<typeVars.length; i++) {
            TypeVariable tv = typeVars[i];
            if (i > 0) {
                ret.append(", ");
            }
            ret.append(tv.getName());
        }
        return ret.append('>').toString();
    }

}
