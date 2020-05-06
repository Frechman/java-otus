package ru.gavrilov.annotation.example;


import java.lang.annotation.*;

/**
 * Originally from https://en.wikipedia.org/wiki/Java_annotation
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD,
        ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE,
        ElementType.PACKAGE, ElementType.FIELD, ElementType.LOCAL_VARIABLE})
@Inherited
public @interface Unfinished {
    public enum Priority {LOW, MEDIUM, HIGH}

    String value();

    String[] changedBy() default "author";

    String[] lastChangedBy() default "";

    Priority priority() default Priority.MEDIUM;

    String createdBy() default "Tully Trautwein"; // default значение вычисляется "динамически"

    String lastChanged() default "2000-01-01";
}
