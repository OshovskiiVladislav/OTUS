package com.oshovskii.otus.controllers;

import com.oshovskii.otus.controllers.interfaces.BookController;
import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.services.interfaces.BookService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.oshovskii.otus.factory.TestBookDtoFactory.createBookDtoWithAllInfoById;
import static com.oshovskii.otus.utils.Utils.EXISTING_BOOK_ID;
import static com.oshovskii.otus.utils.Utils.EXISTING_BOOK_ID_2;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerImplTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    // TODO Какие ещё, более сложные тесты тут могут быть?

    @Test
    void listPage_expectedValidModel_shouldReturnPage() throws Exception {
        // Config
        val expectedBookDto = createBookDtoWithAllInfoById(EXISTING_BOOK_ID);
        val expectedBookDto2 =createBookDtoWithAllInfoById(EXISTING_BOOK_ID_2);

        List<BookDto> expectedBookDtoList = List.of(expectedBookDto, expectedBookDto2);

        given(bookService.findAllBooks()).willReturn(expectedBookDtoList);

        // Call and Verify
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void editPage_expectedValidBookIdAndModel_shouldReturnPage() throws Exception {
        // Config
        val expectedBookDto = createBookDtoWithAllInfoById(EXISTING_BOOK_ID);

        given(bookService.findBookById(EXISTING_BOOK_ID)).willReturn(expectedBookDto);

        // Call and Verify
        mvc.perform(get("/edit?id=" + EXISTING_BOOK_ID))
                .andExpect(model().attribute("book", expectedBookDto))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
    }

    @Test
    void saveBook_expectedValidBookDtoAndModel_shouldReturnPage() throws Exception {
        // Config
        val expectedBookDto = new BookDto();

        // Call and Verify
        mvc.perform(get("/save"))
                .andExpect(status().isOk())
                .andExpect(view().name("save"));
    }

    @Test
    void updateBook_expectedValidBookDtoAndBindingResultAndModel_shouldReturnPage() throws Exception {
        // Config
        val expectedBookDto = createBookDtoWithAllInfoById(EXISTING_BOOK_ID);

        given(bookService.saveBook(expectedBookDto)).willReturn(expectedBookDto);

        // Call and Verify
        mvc.perform(post("/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void saveBook_expectedValidBookDtoAndBindingResultAndModel_shouldReturnPage() throws Exception {
        // Config
        val expectedBookDto = createBookDtoWithAllInfoById(EXISTING_BOOK_ID);

        given(bookService.saveBook(expectedBookDto)).willReturn(expectedBookDto);

        // Call and Verify
        mvc.perform(post("/save"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void deleteBook_expectedValidBookIdAndModel_shouldReturnRedirect() throws Exception {
        // Config
        doNothing().when(bookService).deleteBookById(EXISTING_BOOK_ID);

        // Call and Verify
        mvc.perform(get("/delete/" + EXISTING_BOOK_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
}
