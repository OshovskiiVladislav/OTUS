package com.oshovskii.otus.factory;

import com.oshovskii.otus.domain.FileCsv;

public class TestFileCsvFactory {
    public static FileCsv createFileCsv(int index) {
        return new FileCsv(
                "Question " + index,
                "Right Answer " + index,
                "Incorrect Answer " + index);
    }
}
