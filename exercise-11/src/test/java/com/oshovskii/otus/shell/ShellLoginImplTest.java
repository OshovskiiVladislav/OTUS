package com.oshovskii.otus.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test ShellLoginImpl command")
@SpringBootTest
class ShellLoginImplTest {
    @Autowired
    private Shell shell;

    private static final String GREETING_PATTERN = "Добро пожаловать: %s";
    private static final String DEFAULT_LOGIN = "Mr. Incognito";
    private static final String CUSTOM_LOGIN = "John";
    private static final String COMMAND_LOGIN = "login";
    private static final String COMMAND_LOGIN_SHORT = "l";
    private static final String COMMAND_LOGIN_PATTERN = "%s %s";

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
}
