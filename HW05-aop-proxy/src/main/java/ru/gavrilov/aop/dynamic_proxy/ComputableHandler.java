package ru.gavrilov.aop.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ComputableHandler implements InvocationHandler {

    private final Computable computable;
    private final Set<Method> methodsForLogging;

    public ComputableHandler(Computable computable) {
        this.computable = computable;
        this.methodsForLogging = getMethodForLogging();
    }

    private Set<Method> getMethodForLogging() {
        return Arrays.stream(computable.getClass().getMethods())
                .filter(m -> m.isAnnotationPresent(Log.class))
                .map(this::getMethodsFromInterface)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (methodsForLogging.contains(method)) {
            System.out.println(String.format("executed method: %s, params: %s", method.getName(), Arrays.toString(args)));
        }
        return method.invoke(computable, args);
    }

    private Set<Method> getMethodsFromInterface(Method method) {
        return Arrays.stream(computable.getClass().getInterfaces())
                .map(i -> {
                    try {
                        return i.getDeclaredMethod(method.getName(), method.getParameterTypes());
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}
