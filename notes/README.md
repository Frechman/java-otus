### Notes

----

#### Реализация Offheap IntegerList, используя sun.misc.Unsafe.
Сравнение производительности UnsafeIntList (add/get) с ArrayList при помощи JMH.

> `sun.misc.Unsafe`, `JMH`

- https://www.baeldung.com/java-unsafe
- http://mishadoff.com/blog/java-magic-part-4-sun-dot-misc-dot-unsafe/
- A. Shipilëv https://youtu.be/ESs0bZw8hsA 
- https://2015.jokerconf.com/talks/pangin/ (= https://youtu.be/moHNujaeD2I)
- https://hg.openjdk.java.net/jdk7/jdk7/jdk/file/9b8c96f96a0f/src/share/classes/sun/misc/Unsafe.java
- https://youtu.be/iM3hfEvTDhk Unsafe в Java 9 — халява кончилась? (Алексей Федоров, SECR-2015)

----

#### GC
-XX:+PrintCommandLineFlags -version
