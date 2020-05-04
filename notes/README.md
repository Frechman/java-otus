## Notes

Java topics notes

----

### Реализация Offheap IntegerList, используя sun.misc.Unsafe.
Сравнение производительности UnsafeIntList (add/get) с ArrayList при помощи JMH.

[My implementation UnsafeIntList](./src/main/java/ru/gavrilov/unsafe/UnsafeIntList.java)
> `sun.misc.Unsafe`, `JMH`

- https://www.baeldung.com/java-unsafe
- http://mishadoff.com/blog/java-magic-part-4-sun-dot-misc-dot-unsafe/
- A. Shipilëv https://youtu.be/ESs0bZw8hsA 
- https://2015.jokerconf.com/talks/pangin/ (= https://youtu.be/moHNujaeD2I)
- https://hg.openjdk.java.net/jdk7/jdk7/jdk/file/9b8c96f96a0f/src/share/classes/sun/misc/Unsafe.java
- https://youtu.be/iM3hfEvTDhk Unsafe в Java 9 — халява кончилась? (Алексей Федоров, SECR-2015)

----

### Annotation
[Annotation examples](./src/main/java/ru/gavrilov/annotation/example/Main.java)

```java
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Annotation {
}
```

Параметр `Retention` (тоже аннотация)
- SOURCE – сохраняются только в исходном коде (для разработчиков)
- CLASS – сохраняются только в байт коде (для анализаторов байт-кода)
- RUNTIME – сохраняются в во время выполнения (для среды выполнения)

Параметр `Target` (тоже аннотация)
- TYPE - класс, интерфейс или enum
- FIELD - поле класса
- METHOD - метод
- PARAMETER - параметр
- CONSTRUCTOR - конструктор
- LOCAL_VARIABLE - локальная переменная
- ANNOTATION_TYPE - аннотация
- PACKAGE - пакет
- TYPE_PARAMETER - декларация параметра типа
    - The ElementType.TYPE_PARAMETER target indicates that the annotation can be written on the declaration of a type variable (e.g., class MyClass<T> {...}).
- TYPE_USE - любое использование типа
    - The ElementType.TYPE_USE target indicates that the annotation can be written on any use of a type (e.g., types appearing in declarations, generics, and casts).
- MODULE - модуль

----

### Reflection
[examples](./src/main/java/ru/gavrilov/reflection)
[dynamic proxy example](./src/main/java/ru/gavrilov/reflection/dynamic_proxy)

- https://www.oracle.com/technical-resources/articles/java/javareflection.html
- http://tutorials.jenkov.com/java-reflection/index.html

----

### GC
-XX:+PrintCommandLineFlags -version
