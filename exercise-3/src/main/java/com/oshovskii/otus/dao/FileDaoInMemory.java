package com.oshovskii.otus.dao;

import com.oshovskii.otus.config.ConfigApp;
import org.springframework.stereotype.Repository;

@Repository
public class FileDaoInMemory implements FileDao {

    private final int count;
    private final String filePath;
    private String filePathEn;

    public FileDaoInMemory(ConfigApp configApp) {
        this.filePath = configApp.getFilePath();
        this.filePathEn = configApp.getFilePathEn();
        this.count = configApp.getNumberToCompleteTest();
    }

    @Override
    public String findPathToCsvFile(String fileName) {
        if (fileName.equals("questionsRUS.csv")) {
            return filePath;
        } else if (fileName.equals("questionsEN.csv")) {
            return filePathEn;
        } else {
            throw new IllegalArgumentException("File not found");
        }
    }

    @Override
    public int findCountToCompleteTest(String fileName) {
        if (fileName.equals("questionsRUS.csv") || fileName.equals("questionsEN.csv")) {
            return count;
        } else {
            throw new IllegalArgumentException("File not found");
        }
    }
}
