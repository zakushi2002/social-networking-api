package com.social.networking.api.repository;

import com.social.networking.api.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    Account findAccountByEmail(String email);
    Account findAccountByResetPwdCode(String resetPwdCode);
    Boolean existsByIdAndKindAndStatus(Long id, Integer kind, Integer status);
}
