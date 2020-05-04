package ru.gavrilov.annotation.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by tully.
 */
@Target({ElementType.TYPE_USE})
public @interface NonEmpty {
}
