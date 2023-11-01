package com.social.networking.api.model.criteria;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.model.Account;
import com.social.networking.api.model.Notification;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class NotificationCriteria implements Serializable {
    private Long id;
    @NotNull(message = "accountToId cannot be null!")
    private Long accountToId;
    private Long accountFromId;
    private Integer kind;

    public Specification<Notification> getSpecification() {
        return new Specification<Notification>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Notification> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getAccountToId() != null) {
                    Join<Notification, Account> join = root.join("accountTo", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), getAccountToId()));
                    predicates.add(cb.equal(join.get("status"), SocialNetworkingConstant.STATUS_ACTIVE));
                }
                if (getAccountFromId() != null) {
                    Join<Notification, Account> join = root.join("accountFrom", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), getAccountFromId()));
                    predicates.add(cb.equal(join.get("status"), SocialNetworkingConstant.STATUS_ACTIVE));
                }
                if (getKind() != null) {
                    predicates.add(cb.equal(root.get("kind"), getKind()));
                }
                query.orderBy(cb.desc(root.get("createdDate")));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
