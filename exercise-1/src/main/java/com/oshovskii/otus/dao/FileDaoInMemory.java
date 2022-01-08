package com.oshovskii.otus.dao;

public class FileDaoInMemory implements FileDao {

    private String filePath = "exercise-1/src/main/resources/data/questions.csv";

    @Override
    public String findPathToCsvFile(String fileName) {
        if (fileName.equals("questions.csv")) {
            return filePath;
        } else {
            throw new IllegalArgumentException("File not found");
        }
    }
}
