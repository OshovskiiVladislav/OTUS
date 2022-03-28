package com.oshovskii.otus.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static com.oshovskii.otus.factory.entity.TestEntityFactory.createBook;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
//@ActiveProfiles("unit")
@DisplayName("BookRepository Test")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @DisplayName("findAll() " +
            "with void input " +
            "should find all books test")
    void findAll() {
        // Config
        val sourceBook = createBook(1);

        bookRepository.save(sourceBook);

        // Call
        val actualListBooks = bookRepository.findAll();

        // Verify
        assertEquals(sourceBook, actualListBooks.get(0));
    }

    @Test
    void getById() {
        // Config

        // Call

        // Verify
    }

    @Test
    void save() {
        // Config

        // Call

        // Verify
    }
}