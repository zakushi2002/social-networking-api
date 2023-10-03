package com.social.networking.api.repository;

import com.social.networking.api.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>, JpaSpecificationExecutor<Bookmark> {
    Optional<Bookmark> findFirstByAccountIdAndPostId(Long accountId, Long postId);
}
