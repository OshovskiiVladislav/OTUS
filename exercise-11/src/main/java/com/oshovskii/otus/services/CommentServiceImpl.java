package com.oshovskii.otus.services;

import com.oshovskii.otus.dto.CommentDto;
import com.oshovskii.otus.exceptions.ResourceNotFoundException;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.repositories.CommentRepository;
import com.oshovskii.otus.services.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public CommentDto findCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id: "+ id + " not found"));
        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findAllComments() {
        List<Comment> commentList = commentRepository.findAll();
        return commentList
                .stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto findCommentByText(String text) {
        Comment comment = commentRepository.findByText(text)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with text: "+ text + " not found"));
        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    @Transactional
    public CommentDto saveComment(String text) {
        Comment comment = new Comment(text);
        return modelMapper.map(commentRepository.save(comment), CommentDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public long countComments() {
        return commentRepository.count();
    }

    @Override
    @Transactional
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }
}
