package com.oshovskii.otus.services;

import com.oshovskii.otus.dao.FileDao;
import com.oshovskii.otus.domain.FileCsv;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("Test FileServiceImpl")
@ExtendWith(SpringExtension.class)
@Import(FileServiceImpl.class)
public class FileServiceImplTest {

    @MockBean
    private FileDao fileDao;

    @SpyBean
    private FileServiceImpl fileService;

    private static final String FILE_NAME = "questions.csv";
    private static final int COUNT_TO_COMPLETE_TEST = 2;
    private static final int EXPECTED_SIZE_RESULT = 2;

    @DisplayName("Parse csv file test")
    @Test
    public void parseCsvFileTest_voidInput_shouldReturnListFileCsv() {
        // Config
        when(fileDao.findFileCsvName()).thenReturn(FILE_NAME);

        // Call
        final List<FileCsv> result = fileService.parseCsvFile();

        // Verify
        assertThat(fileService.parseCsvFile()).isNotNull();
        assertEquals(EXPECTED_SIZE_RESULT, result.size());
    }

    @DisplayName("Find number to complete test")
    @Test
    public void numberToCompleteTest_voidInput_shouldInt() {
        // Config
        when(fileDao.findCountToCompleteTest()).thenReturn(COUNT_TO_COMPLETE_TEST);

        // Call
        final int result = fileService.numberToCompleteTest();

        // Verify
        assertThat(fileService.numberToCompleteTest()).isNotNull();
        assertEquals(result, COUNT_TO_COMPLETE_TEST);
    }
}
