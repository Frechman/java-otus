#### Автоматическое логирование.

---
Цель: Понять как реализуется AOP, какие для этого есть технические средства.
Задачи:
- Метод класса можно отметить самодельной аннотацией @Log
- Если метод аннотирован @Log необходимо в консоль выводить значения параметров метода.
    - (executed method: methodName, param: methodArgs)
- Явного вызова логирования быть не должно.

> `reflection, dynamic proxy, InvocationHandler, annotation, Junit5`