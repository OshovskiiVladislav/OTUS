package com.oshovskii.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class FileCsv {
    private final String qwestion;
    private final String rightAnswer;
    private final String incorrectAnswer;
}
