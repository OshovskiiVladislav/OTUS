package com.oshovskii.otus.services;

import com.oshovskii.otus.dto.AuthorDto;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.repositories.AuthorRepository;
import com.oshovskii.otus.services.interfaces.AuthorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> findAll() {
        List<Author> authorList = authorRepository.findAll();
        return authorList
                .stream()
                .map(author -> modelMapper.map(author, AuthorDto.class))
                .collect(Collectors.toList());
    }
}
