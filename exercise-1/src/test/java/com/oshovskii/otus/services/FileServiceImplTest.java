package com.oshovskii.otus.services;

import com.oshovskii.otus.dao.FileDao;
import com.oshovskii.otus.domain.FileCsv;
import com.oshovskii.otus.utils.Parser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.oshovskii.otus.factory.TestFileCsvFactory.createFileCsv;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class FileServiceImplTest {

    @Mock
    private FileDao fileDao;

    @Mock
    private Parser parser;

    @InjectMocks
    private FileServiceImpl fileService;

    private static final String PATH_TO_FILE = "src/main/resources/data/questions.csv";
    private static final String FILE_NAME = "questions.csv";

    @Test
    public void parseCsvFileTest() throws IOException {
        List<FileCsv> fileCsvList = new ArrayList<>();

        final FileCsv fileCsv1 = createFileCsv(1);
        final FileCsv fileCsv2 = createFileCsv(2);

        fileCsvList.add(fileCsv1);
        fileCsvList.add(fileCsv2);

        given(fileDao.findPathToCsvFile(FILE_NAME))
                .willReturn(PATH_TO_FILE);

        given(parser.parseCSV(fileDao.findPathToCsvFile(FILE_NAME)))
                .willReturn(fileCsvList);

        assertThat(fileService.parseCsvFile(FILE_NAME))
                .isNotNull();
    }
}
