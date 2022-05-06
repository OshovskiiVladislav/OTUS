package com.oshovskii.otus.services.implementations;

import com.oshovskii.otus.dto.BookToSaveDto;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.BookRepository;
import com.oshovskii.otus.services.AuthorService;
import com.oshovskii.otus.services.BookService;
import com.oshovskii.otus.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final MutableAclService mutableAclService;
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    /**
     * interface Sid
     * • представляет security identity
     * • имеет реализации GrantedAuthoritySid и PrincipalSid
     *
     * interface ObjectIdentity
     * • представляет уникально идентифицируемую бизнес сущность
     * • содержит идентификатор и тип сущности
     * • реализация по умолчанию ObjectIdentityImpl
     *
     * Интерфейс MutableAclService
     * AclService - методы по чтению объекта
     * • расширяет интерфейс AclService предоставляя возможно по сохранению изменений в хранилище
     * • реализация JdbcMutableAclService сохраняющая ACL в БД через JDBC
     */
    @Transactional
    @Override
    public Book save(BookToSaveDto bookToSaveDto) {
        Author author = authorService.findByName(bookToSaveDto.getAuthor());
        Genre genre = genreService.findByName(bookToSaveDto.getGenre());

        Book toSaveBook = new Book(
                4L,
                author,
                genre,
                bookToSaveDto.getTitle()
        );

        Book savedBook = bookRepository.save(toSaveBook);

        setAclSecurity(savedBook);

        return savedBook;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Book findById(Long bookId) {
        return bookRepository.getById(bookId);
    }

    @Transactional
    @Override
    public void deleteById(Long bookId) {
        bookRepository.deleteBookById(bookId);
    }

    private void setAclSecurity(final Book savedBook){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Sid owner = new PrincipalSid( authentication );
        ObjectIdentity oid = new ObjectIdentityImpl(savedBook.getClass(), savedBook.getId());

        final Sid admin = new GrantedAuthoritySid("ROLE_EDITOR");

        MutableAcl acl = mutableAclService.createAcl( oid );
        acl.setOwner( owner );
        acl.insertAce( acl.getEntries().size(), BasePermission.ADMINISTRATION, admin, true );

        mutableAclService.updateAcl( acl );
    }
}
