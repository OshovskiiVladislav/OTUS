package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.repositories.CommentRepository;
import com.oshovskii.otus.services.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public Optional<Comment> findCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    @Transactional
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Comment> findCommentByText(String text) {
        return commentRepository.findByText(text);
    }

    @Override
    @Transactional
    public Comment saveComment(String text) {
        Comment comment = new Comment(text);
        return commentRepository.save(comment);
    }

    @Override
    public long countComments() {
        return commentRepository.count();
    }

    @Override
    @Transactional
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }
}
