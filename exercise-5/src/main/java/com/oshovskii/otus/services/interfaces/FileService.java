package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.domain.FileCsv;

import java.util.List;

public interface FileService {
    List<FileCsv> parseCsvFile();
    int numberToCompleteTest();
}
