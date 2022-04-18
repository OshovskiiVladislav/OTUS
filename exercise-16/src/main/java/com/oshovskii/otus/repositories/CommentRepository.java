package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
