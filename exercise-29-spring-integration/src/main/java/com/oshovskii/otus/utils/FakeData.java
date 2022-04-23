package com.oshovskii.otus.utils;

import com.github.javafaker.Faker;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FakeData {

    private static final Faker FAKER = Faker.instance();

    public static Faker faker(){
        return FAKER;
    }
}
