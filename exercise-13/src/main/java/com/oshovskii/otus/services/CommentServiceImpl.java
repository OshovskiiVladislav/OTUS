package com.oshovskii.otus.services;

import com.oshovskii.otus.exceptions.ResourceNotFoundException;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.repositories.interfaces.CommentRepository;
import com.oshovskii.otus.services.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findCommentByText(String text) {
        return commentRepository.findByText(text)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with text: " + text + " not found"));
    }

    @Override
    public Comment save(String text) {
        Comment comment = new Comment(null, text);
        return commentRepository.save(comment);
    }
}
