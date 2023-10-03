package com.social.networking.api.repository;

import com.social.networking.api.model.CommentReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CommentReactionRepository extends JpaRepository<CommentReaction, Long>, JpaSpecificationExecutor<CommentReaction> {
    Optional<CommentReaction> findByCommentIdAndAccountIdAndKind(Long commentId, Long accountId, Integer kind);
    void deleteAllByAccountId(Long accountId);
}
