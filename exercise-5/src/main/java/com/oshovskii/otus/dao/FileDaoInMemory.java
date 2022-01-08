package com.oshovskii.otus.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class FileDaoInMemory implements FileDao {

    private final String filePath;
    private final int numberToCompleteTest;

    public FileDaoInMemory(@Value("${test.pathFile}") String filePath, @Value("${test.win}") int numberToCompleteTest) {
        this.filePath = filePath;
        this.numberToCompleteTest = numberToCompleteTest;
    }

    @Override
    public String findFileCsvName() {
        String[] parts = filePath.split("/");
        return parts[parts.length - 1];
    }

    @Override
    public int findCountToCompleteTest() {
        return numberToCompleteTest;
    }
}
