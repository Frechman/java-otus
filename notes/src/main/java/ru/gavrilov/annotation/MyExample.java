package ru.gavrilov.annotation;

import java.util.Arrays;
import java.util.stream.Collectors;

@ThisAnnotation
public class MyExample {

    /**
     * MyExample this - скрытый параметр: ссылка this, который передается в каждый метод.
     */
    @Override
    public boolean equals(@ThisAnnotation MyExample this, Object obj) {
        return super.equals(obj);
    }

    class InnerClass {
        /**
         * MyExample MyExample.this - скрытый параметр: ссылка на this класса MyExample.
         * Конструктору внутреннего класса передается ссылка на объемлющий класс.
         */
        public InnerClass(@ThisAnnotation MyExample MyExample.this) {
        }
    }

}

class B extends MyExample {
    public static void main(String[] args) {
        String annotations = Arrays.stream(B.class.getAnnotations())
                .map(a -> a.annotationType().getSimpleName())
                .collect(Collectors.joining(System.lineSeparator()));
        System.out.println(annotations); //ThisAnnotation
    }
}
