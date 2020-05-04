package ru.gavrilov.reflection;

import java.lang.module.ModuleDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Set;

public class JenkovExamples {
    public static void printGettersSetters(Class aClass) {
        Method[] methods = aClass.getMethods();

        for (Method method : methods) {
            if (isGetter(method)) System.out.println("getter: " + method);
            if (isSetter(method)) System.out.println("setter: " + method);
        }
    }

    public static boolean isGetter(Method method) {
        if (!method.getName().startsWith("get")) return false;
        if (method.getParameterTypes().length != 0) return false;
        if (void.class.equals(method.getReturnType())) return false;
        return true;
    }

    public static boolean isSetter(Method method) {
        if (!method.getName().startsWith("set")) return false;
        if (method.getParameterTypes().length != 1) return false;
        return true;
    }

    public static void exampleArrays() {
        int[] intArray = (int[]) Array.newInstance(int.class, 3);

        Array.set(intArray, 0, 123);
        Array.set(intArray, 1, 456);
        Array.set(intArray, 2, 789);

        System.out.println("intArray[0] = " + Array.get(intArray, 0));
        System.out.println("intArray[1] = " + Array.get(intArray, 1));
        System.out.println("intArray[2] = " + Array.get(intArray, 2));
    }

    public static void main(String[] args) {
        Module myClassModule = JenkovExamples.class.getModule();
        System.out.println("module: " + myClassModule.getName());
        boolean isNamed = myClassModule.isNamed();
        System.out.println("isNamed: " + isNamed);
        boolean isOpen = myClassModule.isOpen("package name");
        System.out.println("isOpen: " + isOpen);
        ModuleDescriptor descriptor = myClassModule.getDescriptor();
//        String moduleName = descriptor.name();
//        System.out.println(moduleName);
//        Set<ModuleDescriptor.Exports> exports = descriptor.exports();
//        boolean isAutomatic = descriptor.isAutomatic();
//        boolean isOpen = descriptor.isOpen();
//        Set packages = descriptor.packages();
//        Set<String> uses = descriptor.uses();
    }
}
