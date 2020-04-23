package ru.gavrilov.qa.lecture05_otus.testing.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("Класс MessageBuilderImpl ")
class MessageBuilderImplTest {

    private static final String NAME_TEMPLATE = "NAME_TEMPLATE";

    private static final String TEMPLATE_TEXT = "Hi! %s %s?";
    private static final String MSG_TEXT = "How are you";
    private static final String SIGN_ALESHA = "Alesha";
    private MessageTemplateProvider provider;
    private MessageBuilderImpl messageBuilder;

    @BeforeEach
    void setUp() {
        provider = mock(MessageTemplateProvider.class);
        messageBuilder = new MessageBuilderImpl(provider);
    }

    @Test
    @DisplayName("должен строить сообщение из темплейта и возвращать корректное сообщение")
    void shouldBuildMessageAndReturnCorrectMessage() {
        when(provider.getMessageTemplate(NAME_TEMPLATE)).thenReturn(TEMPLATE_TEXT);

        String actualMsg = messageBuilder.buildMessage(NAME_TEMPLATE, MSG_TEXT, SIGN_ALESHA);

        assertEquals(String.format(TEMPLATE_TEXT, MSG_TEXT, SIGN_ALESHA), actualMsg);
    }

    @Test
    @DisplayName("должен вызывать метод template provider")
    void should() {
        when(provider.getMessageTemplate(NAME_TEMPLATE)).thenReturn(TEMPLATE_TEXT);

        messageBuilder.buildMessage(NAME_TEMPLATE, MSG_TEXT, SIGN_ALESHA);

        verify(provider, times(1)).getMessageTemplate(NAME_TEMPLATE);
    }

    @Test
    @DisplayName("должен бросать исключение когда шаблон был пустой")
    void shouldThrowExceptionWhenTemplateIsNull() {
        assertThrows(TemplateNotFoundException.class,
                () -> messageBuilder.buildMessage(NAME_TEMPLATE, MSG_TEXT,SIGN_ALESHA));
    }
}