package ru.gavrilov.aop.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ComputableHandler implements InvocationHandler {

    private final Computable computable;
    private final Set<String> methodNamesForLogging;

    public ComputableHandler(Computable computable) {
        this.computable = computable;
        this.methodNamesForLogging = getMethodNamesForLogging();
    }

    private Set<String> getMethodNamesForLogging() {
        return Arrays.stream(computable.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(Log.class))
                .map(Method::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (methodNamesForLogging.contains(method.getName())) {
            System.out.println(String.format("executed method: %s, param: %s", method.getName(), args[0]));
        }
        return method.invoke(computable, args);
    }
}
