package com.social.networking.api.repository;

import com.social.networking.api.model.ConversationAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ConversationAccountRepository extends JpaRepository<ConversationAccount, Long>, JpaSpecificationExecutor<ConversationAccount> {
    Optional<ConversationAccount> findByConversationIdAndAccountId(Long conversationId, Long accountId);
}
