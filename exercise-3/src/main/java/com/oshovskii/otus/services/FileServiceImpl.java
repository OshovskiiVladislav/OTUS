package com.oshovskii.otus.services;

import com.oshovskii.otus.dao.FileDao;
import com.oshovskii.otus.domain.FileCsv;
import com.oshovskii.otus.utils.Parser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileDao dao;
    private final Parser parser;

    @Override
    public List<FileCsv> parseCsvFile(String filePath) {
        try {
            return parser.parseCSV(dao.findPathToCsvFile(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Incorrect filed");
    }

    @Override
    public int numberToCompleteTest(String filePath) {
        return dao.findCountToCompleteTest(filePath);
    }
}
