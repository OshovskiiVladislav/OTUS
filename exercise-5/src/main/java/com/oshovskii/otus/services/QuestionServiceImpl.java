package com.oshovskii.otus.services;

import com.oshovskii.otus.domain.FileCsv;
import com.oshovskii.otus.services.interfaces.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final FileServiceImpl fileService;

    @Override
    public void printQuestions() {
        System.out.println("Test:");
        List<FileCsv> csvFiles = fileService.parseCsvFile();
        for (int i = 0; i < csvFiles.size(); i++) {
            System.out.println();
            System.out.println("Questions: " + csvFiles.get(i).getQwestion());
            System.out.println("1. " + csvFiles.get(i).getRightAnswer());
            System.out.println("2. " + csvFiles.get(i).getIncorrectAnswer());
        }
    }

    @Override
    public String printAnswers(int answer1, int answer2) {
        int count = 0;
        if (answer1 == 1) {
            count++;
        }
        if (answer2 == 1) {
            count++;
        }
        if (count == 2) {
            return "completed the test";
        } else {
            return "failed the test";
        }
    }
}
