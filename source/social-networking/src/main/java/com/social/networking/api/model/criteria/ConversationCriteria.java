package com.social.networking.api.model.criteria;

import com.social.networking.api.model.Conversation;
import com.social.networking.api.model.ConversationAccount;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ConversationCriteria implements Serializable {
    private Long id;
    private String name;
    private Integer kind;

    public Specification<Conversation> getSpecification(Long accountId) {
        return new Specification<Conversation>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Conversation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (id != null) {
                    predicates.add(cb.equal(root.get("id"), id));
                }
                if (name != null) {
                    predicates.add(cb.like(root.get("name"), "%" + name + "%"));
                }
                if (kind != null) {
                    predicates.add(cb.equal(root.get("kind"), kind));
                }
                if (accountId != null) {
                    Root<ConversationAccount> conversationAccountRoot = query.from(ConversationAccount.class);
                    predicates.add(cb.equal(conversationAccountRoot.get("account").get("id"), accountId));
                    predicates.add(cb.equal(conversationAccountRoot.get("conversation").get("id"), root.get("id")));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
