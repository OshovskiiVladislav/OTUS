package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.repositories.interfaces.CommentRepository;
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

    @Transactional
    @Override
    public Comment saveComment(String text) {
        Comment comment = new Comment(text);
        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> findByCommentId(Long id) {
        return commentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Long countComments() {
        return commentRepository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Comment findByCommentText(String text) {
        return commentRepository.findByText(text);
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
}
