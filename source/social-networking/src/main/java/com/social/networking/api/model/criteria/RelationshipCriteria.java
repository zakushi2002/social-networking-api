package com.social.networking.api.model.criteria;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.model.Account;
import com.social.networking.api.model.Relationship;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class RelationshipCriteria implements Serializable {
    private Long id;
    private Long accountId;
    private Long followerId;
    private Integer status;

    public Specification<Relationship> getSpecification() {
        return new Specification<Relationship>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Relationship> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getAccountId() != null) {
                    Join<Relationship, Account> join = root.join("account", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), getAccountId()));
                    predicates.add(cb.equal(join.get("status"), SocialNetworkingConstant.STATUS_ACTIVE));
                }
                if (getFollowerId() != null) {
                    Join<Relationship, Account> join = root.join("follower", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), getFollowerId()));
                    predicates.add(cb.equal(join.get("status"), SocialNetworkingConstant.STATUS_ACTIVE));
                }
                if (getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }
                query.orderBy(cb.desc(root.get("createdDate")));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
