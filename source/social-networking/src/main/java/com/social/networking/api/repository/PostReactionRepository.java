package com.social.networking.api.repository;

import com.social.networking.api.model.PostReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PostReactionRepository extends JpaRepository<PostReaction, Long>, JpaSpecificationExecutor<PostReaction> {
    Optional<PostReaction> findByPostIdAndAccountIdAndKind(Long postId, Long accountId, Integer kind);
    void deleteAllByAccountId(Long accountId);
}
