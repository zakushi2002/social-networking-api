package com.social.networking.api.model.criteria;

import com.social.networking.api.model.Account;
import com.social.networking.api.model.Category;
import com.social.networking.api.model.CommunityMember;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommunityMemberCriteria implements Serializable {
    private Long id;
    private Long communityId;
    private Long accountId;
    private Integer kind;
    private String accountName;
    private String communityName;

    public Specification<CommunityMember> getSpecification() {
        return new Specification<CommunityMember>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<CommunityMember> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (id != null) {
                    predicates.add(cb.equal(root.get("id"), id));
                }
                if (kind != null) {
                    predicates.add(cb.equal(root.get("kind"), kind));
                }
                if (communityId != null || communityName != null) {
                    Join<Category, CommunityMember> communityJoin = root.join("community");
                    if (communityId != null) {
                        predicates.add(cb.equal(communityJoin.get("id"), communityId));
                    }
                    if (communityName != null) {
                        predicates.add(cb.like(cb.lower(communityJoin.get("name")), "%" + communityName.toLowerCase() + "%"));
                    }
                }
                if (accountId != null || accountName != null) {
                    Join<Account, CommunityMember> accountJoin = root.join("account");
                    if (accountId != null) {
                        predicates.add(cb.equal(accountJoin.get("id"), accountId));
                    }
                    if (accountName != null) {
                        predicates.add(cb.like(cb.lower(accountJoin.get("fullName")), "%" + accountName.toLowerCase() + "%"));
                    }
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
