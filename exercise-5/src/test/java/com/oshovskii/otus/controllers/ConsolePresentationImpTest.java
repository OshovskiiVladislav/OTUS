package com.oshovskii.otus.controllers;

import com.oshovskii.otus.services.QuestionServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Test shell command")
@SpringBootTest
public class ConsolePresentationImpTest {

    @MockBean
    private QuestionServiceImpl questionService;

    @Autowired
    private Shell shell;

    private static final String GREETING_PATTERN = "Добро пожаловать: %s";
    private static final String DEFAULT_LOGIN = "AnyUser";
    private static final String CUSTOM_LOGIN = "John";
    private static final String COMMAND_LOGIN = "login";
    private static final String COMMAND_LOGIN_SHORT = "l";
    private static final String COMMAND_PUBLISH_TEST = "test";
    private static final String COMMAND_PUBLISH_ANSWER = "answer 1 1";
    private static final String COMMAND_PUBLISH_TEST_EXPECTED_RESULT = "Тест опубликован";
    private static final String COMMAND_PUBLISH_ANSWER_EXPECTED_RESULT = "completed the test";
    private static final String COMMAND_LOGIN_PATTERN = "%s %s";
    private static final int ANSWER_1 = 1;
    private static final int ANSWER_2 = 1;

    @DisplayName("Should return greeting pattern for all login")
    @Test
    void login_validLoginCommand_shouldReturnExpectedGreeting() {
        String res = (String) shell.evaluate(() -> COMMAND_LOGIN);
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_LOGIN));

        res = (String) shell.evaluate(() -> COMMAND_LOGIN_SHORT);
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_LOGIN));

        res = (String) shell.evaluate(() -> String.format(COMMAND_LOGIN_PATTERN, COMMAND_LOGIN_SHORT, CUSTOM_LOGIN));
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, CUSTOM_LOGIN));
    }

    @DisplayName("Should return CommandNotCurrentlyAvailable if the user logged when trying to execute the test command")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void isPublishEventCommandAvailable_inputVoid_shouldReturnCommandNotCurrentlyAvailableObject() {
        // Config and Call
        Object res =  shell.evaluate(() -> COMMAND_PUBLISH_TEST);

        //  Verify
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

    @DisplayName("Should return the status of the test command execution and call service method  " +
                 "if the command is executed after logging in")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void publishQuestions_validCommand_shouldReturnExpectedMessage() {
        // Config and Call
        shell.evaluate(() -> COMMAND_LOGIN);
        String res = (String) shell.evaluate(() -> COMMAND_PUBLISH_TEST);

        // Verify
        assertThat(res).isEqualTo(COMMAND_PUBLISH_TEST_EXPECTED_RESULT);
        verify(questionService, times(1)).printQuestions();
    }


    @DisplayName("Should return the status of the test command execution and call service method  " +
            "if the command is executed after logging in")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void publishAnswers_validCommandAndIntAnswer1AndAnswer2_shouldReturnExpectedMessage() {
        // Config and Call
        when(questionService.printAnswers(ANSWER_1,ANSWER_2)).thenReturn(COMMAND_PUBLISH_ANSWER_EXPECTED_RESULT);
        shell.evaluate(() -> COMMAND_LOGIN);
        String res = (String) shell.evaluate(() -> COMMAND_PUBLISH_ANSWER);

        // Verify
        verify(questionService, times(1)).printAnswers(ANSWER_1,ANSWER_2);
        assertThat(res).isEqualTo(DEFAULT_LOGIN + " " + COMMAND_PUBLISH_ANSWER_EXPECTED_RESULT);
    }
}
