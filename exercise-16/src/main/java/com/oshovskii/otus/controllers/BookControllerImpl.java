package com.oshovskii.otus.controllers;

import com.oshovskii.otus.controllers.interfaces.BookController;
import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.services.interfaces.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookControllerImpl implements BookController {
    private final BookService bookService;

    @Override
    @GetMapping("/")
    public String listPage(Model model) {
        List<BookDto> bookDtoList = bookService.findAllBooks();
        model.addAttribute("books", bookDtoList);
        return "index";
    }

    @Override
    @GetMapping("/edit")
    public String editPage(@RequestParam("id") Long id, Model model) {
        BookDto bookDto = bookService.findBookById(id);
        model.addAttribute("book", bookDto);
        return "edit";
    }

    @Override
    @GetMapping("/save")
    public String savePage(BookDto bookDto, Model model) {
        model.addAttribute("book", bookDto);
        return "save";
    }

    @Override
    @PostMapping("/edit")
    public String updateBook(@ModelAttribute("book") BookDto bookDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        bookService.saveBook(bookDto);
        return "redirect:/";
    }

    @Override
    @PostMapping("/save")
    public String saveBook(@ModelAttribute("book") BookDto bookDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "save";
        }
        bookService.saveBook(bookDto);
        return "redirect:/";
    }

    // TODO @GetMapping КАК сделать @DeleteMapping ?
    @Override
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id ,Model model) {
        bookService.deleteBookById(id);
        return "redirect:/";
    }
}
