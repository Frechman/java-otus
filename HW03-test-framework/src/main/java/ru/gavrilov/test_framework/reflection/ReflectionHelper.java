package ru.gavrilov.test_framework.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ReflectionHelper {
    private ReflectionHelper() {
    }

    public static Object callMethod(Object object, String name, Object... args) {
        try {
            var method = object.getClass().getDeclaredMethod(name, toClasses(args));
            return callMethod(object, method, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object callMethod(Object object, Method method, Object... args) {
        try {
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Class<?>[] toClasses(Object[] args) {
        return Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
    }

    public static <T> T newInstance(Class<T> clazz) {
        Objects.requireNonNull(clazz);
        try {
            if (clazz.getDeclaredConstructors().length > 1) {
                throw new RuntimeException("");
            }

            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate class: " + clazz.getSimpleName(), e);
        }
    }

    public static List<Method> getMethodsByAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }

    public static List<Method> getStaticMethodsByAnnotation(Class<?> clazz,
                                                            Class<? extends Annotation> annotationClazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotationClazz))
                .filter(method -> (method.getModifiers() & Modifier.STATIC) != 0)
                .collect(Collectors.toList());
    }

}
