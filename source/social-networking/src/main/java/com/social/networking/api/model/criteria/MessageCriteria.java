package com.social.networking.api.model.criteria;

import com.social.networking.api.model.Message;
import com.social.networking.api.model.Account;
import com.social.networking.api.model.Conversation;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class MessageCriteria implements Serializable {
    private Long id;
    @NotNull(message = "conversationId can not be null!")
    private Long conversationId;
    private Long senderId;
    private String content;

    public Specification<Message> getSpecification() {

        return new Specification<Message>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (id != null) {
                    predicates.add(cb.equal(root.get("id"), id));
                }
                if (conversationId != null) {
                    Join<Conversation, Message> conversationJoin = root.join("conversation");
                    predicates.add(cb.equal(conversationJoin.get("id"), conversationId));
                }
                if (senderId != null) {
                    Join<Account, Message> senderJoin = root.join("sender");
                    predicates.add(cb.equal(senderJoin.get("id"), senderId));
                }
                if (content != null) {
                    predicates.add(cb.like(root.get("content"), "%" + content + "%"));
                }
                query.orderBy(cb.desc(root.get("createdDate")));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
