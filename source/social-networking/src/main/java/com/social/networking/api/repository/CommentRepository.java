package com.social.networking.api.repository;

import com.social.networking.api.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
    Page<Comment> findByPostId(Long postId, Pageable pageable);
    List<Comment> findByPostId(Long postId);
}
