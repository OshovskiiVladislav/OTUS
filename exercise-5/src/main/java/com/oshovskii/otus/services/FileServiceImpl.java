package com.oshovskii.otus.services;

import com.oshovskii.otus.dao.FileDao;
import com.oshovskii.otus.domain.FileCsv;
import com.oshovskii.otus.services.interfaces.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileDao dao;

    @Override
    public List<FileCsv> parseCsvFile() {
        return parseCSV(dao.findFileCsvName());
    }

    @Override
    public int numberToCompleteTest() {
        return dao.findCountToCompleteTest();
    }

    private List<FileCsv> parseCSV(String fileName) {
        List<FileCsv> fileCsvs = new ArrayList<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
             BufferedReader bufferedReader = new BufferedReader(
                                             new InputStreamReader(Objects.requireNonNull(inputStream)))) {

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    String[] splitedText = line.split(",");
                    ArrayList<String> columnList = new ArrayList<>();
                    Collections.addAll(columnList, splitedText);
                    FileCsv fileCsv = new FileCsv(
                            columnList.get(0),
                            columnList.get(1),
                            columnList.get(2));

                    fileCsvs.add(fileCsv);
                }
            } catch (IOException e) {
            e.printStackTrace();
        }

        return fileCsvs;
    }
}
