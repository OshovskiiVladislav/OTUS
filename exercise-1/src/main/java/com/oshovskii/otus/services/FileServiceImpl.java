package com.oshovskii.otus.services;

import com.oshovskii.otus.dao.FileDao;
import com.oshovskii.otus.domain.FileCsv;
import com.oshovskii.otus.utils.Parser;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class FileServiceImpl implements FileService{

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
}
