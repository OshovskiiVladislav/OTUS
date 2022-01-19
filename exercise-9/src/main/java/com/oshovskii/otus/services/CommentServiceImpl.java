package com.oshovskii.otus.services;

import com.oshovskii.otus.dto.CommentDto;
import com.oshovskii.otus.exceptions.ResourceNotFoundException;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.repositories.interfaces.CommentRepository;
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

    @Transactional
    @Override
    public CommentDto saveComment(String text) {
        Comment comment = new Comment(text);
        return modelMapper.map(commentRepository.save(comment), CommentDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public CommentDto findByCommentId(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Comment with id: "+ id + " not found"));
        return modelMapper.map(comment, CommentDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public Long countComments() {
        return commentRepository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> findAllComments() {
        List<Comment> commentList = commentRepository.findAll();
        return commentList
                .stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public CommentDto findByCommentText(String text) {
        return modelMapper.map(commentRepository.findByText(text), CommentDto.class);
    }

    @Transactional
    @Override
    public void updateTextByCommentId(Long id, String text) {
        commentRepository.updateTextById(id, text);
    }

    @Transactional
    @Override
    public void deleteByCommentId(Long id) {
        commentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }
}
