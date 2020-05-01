package ru.gavrilov.test_framework.core;

import ru.gavrilov.test_framework.annotation.AfterAll;
import ru.gavrilov.test_framework.annotation.BeforeAll;
import ru.gavrilov.test_framework.service.OutputService;
import ru.gavrilov.test_framework.annotation.Test;
import ru.gavrilov.test_framework.reflection.Helper;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class TestEngine {

    private Class<?> clazz;
    private List<Method> testMethods;
    private List<Method> beforeAllMethods;
    private List<Method> afterAllMethods;
    private OutputService outputService;

    private Map<String, List<Throwable>> results = new HashMap<>();

    public TestEngine(Class<?> clazz, OutputService outputService) {
        this.clazz = clazz;
        this.testMethods = Helper.getMethodsWithAnnotation(Test.class, clazz.getDeclaredMethods());
        this.beforeAllMethods = Helper.getMethodsWithAnnotation(BeforeAll.class, clazz.getDeclaredMethods());
        this.afterAllMethods = Helper.getMethodsWithAnnotation(AfterAll.class, clazz.getDeclaredMethods());
        this.outputService = outputService;
    }

    public void runTests() {
        showStartingMsg();

        beforeAllMethods.forEach(m -> Helper.callMethod(null, m)); //methods must be static

        for (Method method : testMethods) {
            outputService.out(String.format("Start method: %s...", method.getName()));

            TestExceptionHandler testCase = new TestExceptionHandler(
                    new TestCase(clazz, method.getName())
            );
            testCase.run();

            if (!testCase.getErrors().isEmpty()) {
                results.put(method.getName(), new ArrayList<>(testCase.getErrors()));
            }
        }

        afterAllMethods.forEach(m -> Helper.callMethod(null, m)); //methods must be static

        showStatistics(outputService);
    }

    private void showStartingMsg() {
        outputService.out("==========================================");
        outputService.out(String.format("Testing class: %s...", clazz.getCanonicalName()));
        outputService.out("==========================================");
    }

    private void showStatistics(OutputService outputService) {
        outputService.out(
                String.join(System.lineSeparator(),
                        "==========================================",
                        String.format("Запущено тестов: %d", testMethods.size()),
                        String.format("Пройдено успешно: %d", testMethods.size() - results.size()),
                        String.format("Не пройдено: %d", results.size()),
                        "==========================================",
                        getResultFailedTest()
                )
        );
    }

    private String getResultFailedTest() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<Throwable>> entry : results.entrySet()) {
            sb.append("Метод ").append(entry.getKey()).append(" не пройден!");
            sb.append(System.lineSeparator());
            sb.append("Ошибки: ").append(System.lineSeparator());
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
