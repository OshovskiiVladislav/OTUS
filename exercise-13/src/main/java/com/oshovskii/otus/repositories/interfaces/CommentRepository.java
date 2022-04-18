package com.oshovskii.otus.repositories.interfaces;

import com.oshovskii.otus.models.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, String> {
    Optional<Comment> findByText(String text);
}
