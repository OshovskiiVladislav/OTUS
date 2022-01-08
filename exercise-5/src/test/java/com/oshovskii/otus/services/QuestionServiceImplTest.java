package com.oshovskii.otus.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@DisplayName("Test QuestionServiceImpl")
@ExtendWith(SpringExtension.class)
@Import(QuestionServiceImpl.class)
public class QuestionServiceImplTest {
    @MockBean
    private FileServiceImpl fileService;

    @SpyBean
    private QuestionServiceImpl questionService;

    private static final int ANSWER_1 = 1;
    private static final int ANSWER_2 = 1;
    private static final String ANSWER_EXPECTED_RESULT = "completed the test";

    @DisplayName("Method printAnswers should return a correct answer")
    @Test
    public void printAnswers_validAnswer1AndAnswer2_shouldReturnExpectedMessage() {
        // Call
        final String result = questionService.printAnswers(ANSWER_1, ANSWER_2);

        // Verify
        assertEquals(ANSWER_EXPECTED_RESULT, result);
    }

    @DisplayName("Method printQuestions ")
    @Test
    public void printQuestions_voidInput_shouldReturnSOUTMessage() {
        // Call
        questionService.printQuestions();

        // Verify
        verify(questionService, Mockito.times(1)).printQuestions();
    }
}
