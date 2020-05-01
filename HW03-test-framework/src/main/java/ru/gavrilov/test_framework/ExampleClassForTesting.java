package ru.gavrilov.test_framework;

import ru.gavrilov.test_framework.annotation.*;

public class ExampleClassForTesting {

    @BeforeAll
    static void beforeAll() {
        System.out.println("beforeAll");
    }

    @Before
    void setUp() {
        System.out.println("before");
    }

    @Before
    void setUp2() {
        System.out.println("before2");
    }

    @Test
    void runTest() {
        System.out.println("run1");
    }

    @Test
    void runTest2() {
        System.out.println("run2");
        throw new NullPointerException();
    }

    @After
    void tearDown() {
        System.out.println("after");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("afterAll");
    }
}