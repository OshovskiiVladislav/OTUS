package com.oshovskii.otus.services;

import com.oshovskii.otus.domain.FileCsv;

import java.util.List;

public interface FileService {
    List<FileCsv> parseCsvFile(String filePath);
}
