package com.social.networking.api.repository;

import com.social.networking.api.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {
    Optional<Message> findFirstByConversationIdAndContentOrderByCreatedDateDesc(Long conversationId, String content);
}
