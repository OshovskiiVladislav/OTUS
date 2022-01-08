package com.oshovskii.otus.utils;

import com.oshovskii.otus.domain.FileCsv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public List<FileCsv> parseCSV(String path) throws IOException {
        List<FileCsv> fileCsvs = new ArrayList<>();
        List<String> fileLines = Files.readAllLines(Paths.get(path));
        for (String fileLine : fileLines) {
            String[] splitedText = fileLine.split(",");
            ArrayList<String> columnList = new ArrayList<>();
            for (int i = 0; i < splitedText.length; i++) {
                columnList.add(splitedText[i]);
            }
            FileCsv fileCsv = new FileCsv(
                    columnList.get(0),
                    columnList.get(1),
                    columnList.get(2));

            fileCsvs.add(fileCsv);
        }
        return fileCsvs;
    }
}
