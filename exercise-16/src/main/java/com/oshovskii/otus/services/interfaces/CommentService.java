package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> findAll();
}
