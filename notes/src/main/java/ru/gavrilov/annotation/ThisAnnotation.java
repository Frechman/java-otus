package ru.gavrilov.annotation;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ThisAnnotation {
}
