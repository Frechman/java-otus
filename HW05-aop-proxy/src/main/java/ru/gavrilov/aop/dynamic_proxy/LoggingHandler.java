package ru.gavrilov.aop.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class LoggingHandler implements InvocationHandler {

    private final Class<?> implClass;
    private final Object instance;
    private final Set<Method> methodsForLogging;

    public <T> LoggingHandler(Object originObject, Class<? extends T> implClass) {
        this.implClass = implClass;
        this.instance = originObject;
        this.methodsForLogging = getMethodForLogging(implClass);
    }

    private Set<Method> getMethodForLogging(Class<?> aClass) {
        return Arrays.stream(aClass.getMethods())
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
        return method.invoke(instance, args);
    }

    private Set<Method> getMethodsFromInterface(Method methodFromClass) {
        return Arrays.stream(implClass.getInterfaces())
                .map(Class::getDeclaredMethods)
                .flatMap(Arrays::stream)
                .filter(m -> m.getName().equals(methodFromClass.getName()))
                .filter(m -> Arrays.equals(m.getParameterTypes(), methodFromClass.getParameterTypes()))
                .collect(Collectors.toSet());
    }
}
