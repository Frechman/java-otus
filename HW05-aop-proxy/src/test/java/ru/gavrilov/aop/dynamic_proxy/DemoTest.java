package ru.gavrilov.aop.dynamic_proxy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Demo ")
class DemoTest {

    private static final String EOL = System.lineSeparator();
    private static final String TEXT_TO_PRINT1 = "executed method: compute, params: [2147483647]";
    private static final String TEXT_TO_PRINT2 = "executed method: compute, params: [6]";
    private static final String TEXT_TO_PRINT3 = "executed method: compute, params: [1]";
    private static final String TEXT_TO_PRINT4 = "executed method: compute, params: [-2147483648]";
    private static final String TEXT_TO_PRINT5 = "executed method: compute, params: [string Integer.MAX_VALUE]";
    private static final String TEXT_TO_PRINT6 = "executed method: compute, params: [-2147483648, 2147483647]";

    private ByteArrayOutputStream baos;
    private PrintStream backup;

    @BeforeEach
    void setUp() {
        backup = System.out;

        baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
    }

    @AfterEach
    void tearDown() {
        System.setOut(backup);
    }

    @Test
    @DisplayName("должно печатать лог сообщение")
    void shouldPrintLogMessage() {
        new Demo().action();

        assertThat(baos.toString())
                .isEqualTo(TEXT_TO_PRINT1 + EOL + TEXT_TO_PRINT2 + EOL + TEXT_TO_PRINT3 + EOL + TEXT_TO_PRINT4 + EOL);
    }

    @Test
    @DisplayName("должно печатать в консоль \"" + TEXT_TO_PRINT1 + "\"")
    void shouldPrintLogMessageInConsole() {
        Computable calculator = Ioc.createObject(Calculator.class);
        calculator.compute(Integer.MAX_VALUE);

        assertThat(baos.toString()).isEqualTo(TEXT_TO_PRINT1 + EOL);
    }

    @Test
    @DisplayName("должно печатать в консоль \"" + TEXT_TO_PRINT5 + "\"")
    void shouldPrintLogMessageInConsole5() {
        Computable calculator = Ioc.createObject(Calculator.class);
        calculator.compute("string Integer.MAX_VALUE");

        assertThat(baos.toString()).isEqualTo(TEXT_TO_PRINT5 + EOL);
    }

    @Test
    @DisplayName("должно печатать в консоль \"" + TEXT_TO_PRINT6 + "\"")
    void shouldPrintLogMessageInConsole6() {
        Computable calculator = Ioc.createObject(Calculator.class);
        calculator.compute(Integer.MIN_VALUE, Integer.MAX_VALUE);

        assertThat(baos.toString()).isEqualTo(TEXT_TO_PRINT6 + EOL);
    }


    @Test
    @DisplayName("не должно печатать в консоль")
    void shouldNotPrintLogMessage() {
        Ioc.<Computable>createObject(Calculator.class).something(Integer.MIN_VALUE);

        assertThat(baos.toString()).isEmpty();
    }
}