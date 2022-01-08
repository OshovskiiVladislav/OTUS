package com.oshovskii.otus.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class FileDaoInMemory implements FileDao {

    @Value("${test.pathFile}")
    private String filePath;

    @Value("${test.win}")
    private int numberToCompleteTest;

    @Override
    public String findPathToCsvFile(String fileName) {
        if (fileName.equals("questions.csv")) {
            return filePath;
        } else {
            throw new IllegalArgumentException("File not found");
        }
    }

    @Override
    public int findCountToCompleteTest(String fileName) {
        if (fileName.equals("questions.csv")) {
            return numberToCompleteTest;
        } else {
            throw new IllegalArgumentException("File not found");
        }
    }
}
