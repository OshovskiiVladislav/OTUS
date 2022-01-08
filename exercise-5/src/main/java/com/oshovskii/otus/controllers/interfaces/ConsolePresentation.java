package com.oshovskii.otus.controllers.interfaces;

public interface ConsolePresentation {
    String login(String userName);
    String publishQuestions();
    String publishAnswers(int answer1, int answer2);
}
