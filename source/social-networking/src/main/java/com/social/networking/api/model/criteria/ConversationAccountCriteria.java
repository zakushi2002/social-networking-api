package com.social.networking.api.model.criteria;

import com.social.networking.api.model.Account;
import com.social.networking.api.model.Conversation;
import com.social.networking.api.model.ConversationAccount;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ConversationAccountCriteria implements Serializable {
    private Long id;
    private Long conversationId;
    private Long accountId;

    public Specification<ConversationAccount> getSpecification() {
        return new Specification<ConversationAccount>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<ConversationAccount> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (id != null) {
                    predicates.add(cb.equal(root.get("id"), id));
                }
                if (conversationId != null) {
                    Join<Conversation, ConversationAccount> conversationJoin = root.join("conversation");
                    predicates.add(cb.equal(conversationJoin.get("id"), conversationId));
                }
                if (accountId != null) {
                    Join<Account, ConversationAccount> accountJoin = root.join("account");
                    predicates.add(cb.equal(accountJoin.get("id"), accountId));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
