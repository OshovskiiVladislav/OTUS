package com.oshovskii.otus.models;

import com.oshovskii.otus.utils.FakeData;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cat {

    private Long id;
    private String name;
    private String breed;

    public Cat(Long id) {
        this.id = id;
        this.name = FakeData.faker().cat().name();
        this.breed = FakeData.faker().cat().breed();
    }
}
