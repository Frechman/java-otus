package ru.gavrilov.annotation.example;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
//@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Repeatable(Role.List.class)
public @interface Role {
    String value();

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @interface List{
        Role[] value();
    }
}
