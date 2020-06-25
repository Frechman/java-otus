package ru.gavrilov.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @author gavrilov-sv
 * created on 25.06.2020
 */
public class ReflectionUtils {

    private ReflectionUtils() {
    }

    public static Object getFieldValue(Field field, Object object) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void setFieldValue(Object object, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T instantiate(Constructor<T> constructor) {
        try {
            return constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the wrapper type for the supplied primitive type.
     *
     * @param type the primitive type for which to retrieve the wrapper type
     * @return the corresponding wrapper type or supplied type if the
     * supplied type is not a primitive type.
     *
     * @see org.junit.platform.commons.util;
     */
    public static Class<?> getWrapperType(Class<?> type) {
        if (!type.isPrimitive()) {
            return type;
        }
        return primitiveToWrapperMap.get(type);
    }

    /**
     * Internal cache of primitive types mapped to their wrapper types.
     *
     * @see org.junit.platform.commons.util;
     */
    private static final Map<Class<?>, Class<?>> primitiveToWrapperMap;

    static {
        Map<Class<?>, Class<?>> primitivesToWrappers = new IdentityHashMap<>(8);

        primitivesToWrappers.put(boolean.class, Boolean.class);
        primitivesToWrappers.put(byte.class, Byte.class);
        primitivesToWrappers.put(char.class, Character.class);
        primitivesToWrappers.put(short.class, Short.class);
        primitivesToWrappers.put(int.class, Integer.class);
        primitivesToWrappers.put(long.class, Long.class);
        primitivesToWrappers.put(float.class, Float.class);
        primitivesToWrappers.put(double.class, Double.class);

        primitiveToWrapperMap = Collections.unmodifiableMap(primitivesToWrappers);
    }
}
