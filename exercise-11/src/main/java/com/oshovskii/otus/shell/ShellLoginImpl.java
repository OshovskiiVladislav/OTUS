package com.oshovskii.otus.shell;

import com.oshovskii.otus.shell.interfaces.ShellLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class ShellLoginImpl implements ShellLogin {
    private String userName;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "Mr. Incognito") String userName) {
        this.userName = userName;
        return String.format("Добро пожаловать: %s", userName);
    }

    public String getCurrentUserName() {
        return userName;
    }
}