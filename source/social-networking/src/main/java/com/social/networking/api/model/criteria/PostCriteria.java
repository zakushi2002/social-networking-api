package com.social.networking.api.model.criteria;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.model.Account;
import com.social.networking.api.model.Post;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostCriteria implements Serializable {
    private Long id;
    private Long accountId;
    private String accountName;
    private String keyword;
    private Integer kind;
    private Integer status;
    private Integer privacy;

    public Specification<Post> getSpecification() {
        return new Specification<Post>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getKind() != null) {
                    predicates.add(cb.equal(root.get("kind"), getKind()));
                }
                if (getPrivacy() != null) {
                    predicates.add(cb.equal(root.get("privacy"), getPrivacy()));
                }
                if (!StringUtils.isEmpty(getKeyword())) {
                    predicates.add(cb.like(cb.lower(root.get("content")), "%" + getKeyword().toLowerCase() + "%"));
                }
                if (getAccountId() != null) {
                    Join<Account, Post> join = root.join("account", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), getAccountId()));
                    predicates.add(cb.equal(join.get("status"), SocialNetworkingConstant.STATUS_ACTIVE));
                }
                if (!StringUtils.isEmpty(getAccountName())) {
                    Join<Account, Post> join = root.join("account", JoinType.INNER);
                    predicates.add(cb.like(cb.lower(join.get("fullName")), "%" + getAccountName().toLowerCase() + "%"));
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
