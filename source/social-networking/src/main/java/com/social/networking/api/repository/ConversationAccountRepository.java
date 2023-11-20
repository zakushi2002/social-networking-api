package com.social.networking.api.repository;

import com.social.networking.api.model.ConversationAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ConversationAccountRepository extends JpaRepository<ConversationAccount, Long>, JpaSpecificationExecutor<ConversationAccount> {
}
