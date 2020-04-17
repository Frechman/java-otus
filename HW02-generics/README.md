Цель: изучить как устроена стандартная коллекция ArrayList. Попрактиковаться в создании своей коллекции.

- Написать свою реализацию ArrayList.
- class MyArrayList<T> implements List<T>{...}
- Проверить, что на ней работают методы из java.util.Collections
  - addAll(Collection<? super T> c, T... elements)
  - static <T> void	copy(List<? super T> dest, List<? extends T> src)
  - static <T> void	sort(List<T> list, Comparator<? super T> c)