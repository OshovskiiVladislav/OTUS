package com.oshovskii.otus.controllers.interfaces;

import com.oshovskii.otus.dto.BookDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public interface BookController {
    String listPage(Model model);
    String editPage(Long id, Model model);
    String savePage(BookDto bookDto, Model model);

    String deleteBook(Long id);
    String updateBook(BookDto bookDto, BindingResult bindingResult);
    String saveBook(BookDto bookDt, BindingResult bindingResult);
}
