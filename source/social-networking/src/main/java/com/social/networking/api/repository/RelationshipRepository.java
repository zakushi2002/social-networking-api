package com.social.networking.api.repository;

import com.social.networking.api.model.Relationship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface RelationshipRepository extends JpaRepository<Relationship, Long>, JpaSpecificationExecutor<Relationship> {
    Optional<Relationship> findByAccountIdAndFollowerId(Long accountId, Long followerId);
    Page<Relationship> findAllByAccountId(Long accountId, Pageable pageable);
    Page<Relationship> findAllByFollowerId(Long followerId, Pageable pageable);
    List<Relationship> findAllByFollowerId(Long followerId);
    List<Relationship> findAllByAccountId(Long accountId);
}
