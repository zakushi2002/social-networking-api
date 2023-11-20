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
public class AnnouncementCriteria implements Serializable {
    private Long id;
    private Long notificationId;
    private Long accountId;

    public Specification<Announcement> getSpecification() {
        return new Specification<Announcement>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Announcement> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getNotificationId() != null) {
                    Join<Notification, Announcement> notificationJoin = root.join("notification");
                    predicates.add(cb.equal(notificationJoin.get("id"), getNotificationId()));
                }
                if (getAccountId() != null) {
                    Join<Account, Announcement> accountJoin = root.join("account");
                    predicates.add(cb.equal(accountJoin.get("id"), getAccountId()));
                }
                query.orderBy(cb.desc(root.get("createdDate")));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
