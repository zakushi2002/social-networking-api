package com.social.networking.api.model.criteria;

import com.social.networking.api.model.Account;
import com.social.networking.api.model.Announcement;
import com.social.networking.api.model.Notification;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class NotificationCriteria implements Serializable {
    private Long id;
    private Integer kind;
    private Integer status;

    public Specification<Notification> getSpecification(Long accountId) {
        return new Specification<Notification>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Notification> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getKind() != null) {
                    predicates.add(cb.equal(root.get("kind"), getKind()));
                }
                if (accountId != null) {
                    Root<Announcement> announcementRoot = query.from(Announcement.class);
                    Join<Announcement, Notification> join = announcementRoot.join("notification", JoinType.INNER);
                    Join<Announcement, Account> joinAccount = announcementRoot.join("account", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), root.get("id")));
                    predicates.add(cb.equal(joinAccount.get("id"), accountId));
                    if (getStatus() != null) {
                        predicates.add(cb.equal(announcementRoot.get("status"), getStatus()));
                    }
                }
                query.orderBy(cb.desc(root.get("createdDate")));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
