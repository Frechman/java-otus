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
    private static final String TEXT_TO_PRINT1 = "executed method: compute, param: 2147483647";
    private static final String TEXT_TO_PRINT2 = "executed method: compute, param: 6";
    private static final String TEXT_TO_PRINT3 = "executed method: compute, param: 1";
    private static final String TEXT_TO_PRINT4 = "executed method: compute, param: -2147483648";

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
        Computable calculator = Ioc.createCalculator();
        calculator.compute(Integer.MAX_VALUE);

        assertThat(baos.toString()).isEqualTo(TEXT_TO_PRINT1 + EOL);
    }

    @Test
    @DisplayName("не должно печатать в консоль")
    void shouldNotPrintLogMessage() {
        Ioc.createCalculator().something(Integer.MIN_VALUE);

        assertThat(baos.toString()).isEmpty();
    }
}