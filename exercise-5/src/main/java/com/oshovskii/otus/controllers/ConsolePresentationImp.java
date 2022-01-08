package com.oshovskii.otus.controllers;

import com.oshovskii.otus.controllers.interfaces.ConsolePresentation;
import com.oshovskii.otus.services.QuestionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class ConsolePresentationImp implements ConsolePresentation {

    private final QuestionServiceImpl questionServiceImpl;
    private String userName;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "AnyUser") String userName) {
        this.userName = userName;
        return String.format("Добро пожаловать: %s", userName);
    }

    @ShellMethod(value = "Publish questions", key = {"t", "test"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String publishQuestions() {
        questionServiceImpl.printQuestions();
        return "Тест опубликован";
    }

    @ShellMethod(value = "Publish answers", key = {"a", "ans", "answer", "answers"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String publishAnswers(int answer1, int answer2) {
        return userName + " " + questionServiceImpl.printAnswers(answer1, answer2);
    }

    private Availability isPublishEventCommandAvailable() {
        return userName == null? Availability.unavailable("Сначала залогиньтесь"): Availability.available();
    }
}
