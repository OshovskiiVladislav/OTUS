package com.oshovskii.otus.config;

import com.oshovskii.otus.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookCntHealthIndicator implements HealthIndicator {

    private final BookService bookService;

    @Override
    public Health health() {
        Long booksCount = bookService.count();
        return booksCount == 0 ?
                Health.down().withDetail("message", "No books!!!").build() :
                Health.up().withDetail("message", "There " + booksCount + " books in the library!").build();
    }
}
