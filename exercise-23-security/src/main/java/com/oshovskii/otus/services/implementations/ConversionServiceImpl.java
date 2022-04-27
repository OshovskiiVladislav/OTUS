package com.oshovskii.otus.services.implementations;

import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.repositories.NoteRepository;
import com.oshovskii.otus.services.ConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ConversionServiceImpl implements ConversionService {

    private final NoteRepository noteRepository;

    @Override
    public List<BookDto> fromDomain(List<Book> books) {
        List<BookDto> booksDto = new ArrayList<>();
        for (Book book : books) {
            booksDto.add(fromDomain(book));
        }
        return booksDto;
    }

    @Override
    public BookDto fromDomain(Book book) {
        long count = noteRepository.countByBookId(book.getId());
        return new BookDto(book.getId(), book.getAuthor(), book.getGenre(), book.getTitle(), count);
    }

    @Override
    public Book fromDto(BookDto bookDto) {
        return new Book(bookDto.getId(), bookDto.getAuthor(), bookDto.getGenre(), bookDto.getTitle());
    }
}
