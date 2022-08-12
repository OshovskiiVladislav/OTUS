package com.oshovskii.otus.controllers;

import com.oshovskii.otus.dto.AuthorDto;
import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.dto.BookToSaveDto;
import com.oshovskii.otus.dto.GenreDto;
import com.oshovskii.otus.services.interfaces.AuthorService;
import com.oshovskii.otus.services.interfaces.BookService;
import com.oshovskii.otus.services.interfaces.GenreService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class BookControllerImpl {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final ModelMapper modelMapper;

    @GetMapping("/")
    public String listPage(Model model) {
        List<BookDto> bookDtoList = bookService.findAllBooks();
        model.addAttribute("books", bookDtoList);
        return "index";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") Long id, Model model) {
        BookDto bookDto = bookService.findBookById(id);
        model.addAttribute("book", bookDto);
        return "edit";
    }

    @GetMapping("/save")
    public String savePage(Model model) {
        BookToSaveDto bookToSaveDto = new BookToSaveDto();

        model.addAttribute("bookDto", bookToSaveDto);

        List<AuthorDto> authorsList = authorService.findAll();
        model.addAttribute("authorsList", authorsList);

        List<GenreDto> genresList = genreService.findAll();
        model.addAttribute("genresList", genresList);

        return "save";
    }

    @PostMapping("/edit")
    public String updateBook(@ModelAttribute("book") BookDto bookDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        bookService.updateBook(bookDto);
        return "redirect:/";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute("bookDto") BookToSaveDto bookToSaveDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "save";
        }

        BookDto bookDto = new BookDto(
                bookToSaveDto.getId(),
                bookToSaveDto.getTitle(),
                Set.of(modelMapper.map(bookToSaveDto.getAuthor(), AuthorDto.class)),
                Set.of(modelMapper.map(bookToSaveDto.getGenre(), GenreDto.class))
        );

        bookService.saveBook(bookDto);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBookById(id);
        return "redirect:/";
    }
}
