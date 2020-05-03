package ru.gavrilov.test_framework.core;

import ru.gavrilov.test_framework.annotation.*;
import ru.gavrilov.test_framework.annotation.Test;
import ru.gavrilov.test_framework.service.OutputService;
import ru.gavrilov.test_framework.reflection.ReflectionHelper;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class TestEngine {

    private int count = 0;

    private final Class<?> clazz;
    private final List<Method> testMethods;
    private final List<Method> beforeAllMethods;
    private final List<Method> afterAllMethods;
    private final List<Method> beforeMethods;
    private final List<Method> afterMethods;

    private OutputService outputService;

    private Map<String, List<Throwable>> results = new HashMap<>();

    public TestEngine(Class<?> clazz, OutputService outputService) {
        this.clazz = clazz;
        this.testMethods = ReflectionHelper.getMethodsByAnnotation(clazz, Test.class);
        this.beforeAllMethods = ReflectionHelper.getStaticMethodsByAnnotation(clazz, BeforeAll.class);
        this.afterAllMethods = ReflectionHelper.getStaticMethodsByAnnotation(clazz, AfterAll.class);
        this.beforeMethods = ReflectionHelper.getMethodsByAnnotation(clazz, Before.class);
        this.afterMethods = ReflectionHelper.getMethodsByAnnotation(clazz, After.class);
        this.outputService = outputService;
    }

    public void runTests() {
        showStartingMsg();

        try {
            beforeAllMethods.forEach(m -> ReflectionHelper.callMethod(null, m));

            startTestSuite();

        } catch (Throwable t) {
            results.put("из @BeforeAll методов", Collections.singletonList(t));
        } finally {
            try {
                afterAllMethods.forEach(m -> ReflectionHelper.callMethod(null, m));
            } catch (Throwable t) {
                results.put("из @AfterAll методов", Collections.singletonList(t));
            }
        }

        showStatistics(outputService);
    }

    private void startTestSuite() {
        for (Method method : testMethods) {
            count++;

            outputService.out(String.format("Выполняется метод: %s...", method.getName()));

            TestCase testCase = new TestCase(clazz, method, beforeMethods, afterMethods);
            testCase.run();

            if (!testCase.getErrors().isEmpty()) {
                results.put(method.getName(), new ArrayList<>(testCase.getErrors()));
                count--;
            }
        }
    }

    private void showStartingMsg() {
        outputService.out("==========================================");
        outputService.out(String.format("Тестируем класс: %s...", clazz.getCanonicalName()));
        outputService.out("==========================================");
    }

    private void showStatistics(OutputService outputService) {
        outputService.out(
                String.join(System.lineSeparator(),
                        "==========================================",
                        String.format("Всего тестов: %d", testMethods.size()),
                        String.format("Запущено тестов: %d", count),
                        String.format("Пройдено успешно: %d", count),
                        String.format("Не пройдено: %d", testMethods.size() - count),
                        "==========================================",
                        getResultFailedTest()
                )
        );
    }

    private String getResultFailedTest() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<Throwable>> entry : results.entrySet()) {
            sb.append("Метод ").append(entry.getKey()).append(" упал с ошибками:");
            sb.append(System.lineSeparator());
            sb.append(msgErrors(entry.getValue()));
            sb.append(System.lineSeparator()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    private String msgErrors(List<? extends Throwable> exceptionList) {
        return exceptionList.stream()
                .map(Throwable::getMessage)
                .collect(Collectors.joining(System.lineSeparator()));
    }

}
