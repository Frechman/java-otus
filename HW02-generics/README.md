#### Написать свою реализацию ArrayList

---
Цель: изучить как устроена стандартная коллекция ArrayList. Попрактиковаться в создании своей коллекции.

- Написать свою реализацию ArrayList.
- class MyArrayList<T> implements List<T>{...}
- Проверить, что на ней работают методы из java.util.Collections
  - addAll(Collection<? super T> c, T... elements)
  - static <T> void	copy(List<? super T> dest, List<? extends T> src)
  - static <T> void	sort(List<T> list, Comparator<? super T> c)
---

Useful links:
<br/>
[JMH samples hg.openjdk.java.net](https://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/)
<br/>
[JMH samples Github, gradle example](https://github.com/melix/jmh-gradle-example)
<br/>
[JMH gradle plugin](https://github.com/melix/jmh-gradle-plugin/tree/master#using-jmh-gradle-plugin-with-shadow-plugin)
<br/>
[gradle.build for JMH (StackOverflow)](https://stackoverflow.com/questions/20443997/how-to-use-jmh-with-gradle)
<br/>
[Settings gradle.build for JMH (dev.to)](https://dev.to/o_a_e/jmh-with-gradle--from-easy-to-simple-52ec)