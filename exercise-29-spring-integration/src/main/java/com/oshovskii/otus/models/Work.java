package com.oshovskii.otus.models;

import com.oshovskii.otus.utils.FakeData;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Work {

    private Long catId;
    private int result;
    private String title;

    public Work(Long catId) {
        this.catId = catId;
        this.result = FakeData.faker().random().nextInt(1, 100);
        this.title = FakeData.faker().job().title();
    }
}
