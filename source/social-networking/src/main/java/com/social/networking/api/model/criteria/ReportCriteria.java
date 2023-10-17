package com.social.networking.api.model.criteria;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.model.Account;
import com.social.networking.api.model.Comment;
import com.social.networking.api.model.Post;
import com.social.networking.api.model.Report;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReportCriteria implements Serializable {
    private Long id;
    private Long objectId;
    private Integer kind;
    private Integer status;

    public Specification<Report> getSpecification() {
        return new Specification<Report>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Report> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (id != null) {
                    predicates.add(cb.equal(root.get("id"), id));
                }
                if (kind != null) {
                    predicates.add(cb.equal(root.get("kind"), kind));
                    if (objectId != null) {
                        if (kind.equals(SocialNetworkingConstant.REPORT_KIND_POST)) {
                            Join<Report, Post> postJoin = root.join("objectId", JoinType.INNER);
                            predicates.add(cb.equal(postJoin, objectId));
                        } else if (kind.equals(SocialNetworkingConstant.REPORT_KIND_COMMENT)) {
                            Join<Report, Comment> commentJoin = root.join("objectId", JoinType.INNER);
                            predicates.add(cb.equal(commentJoin, objectId));
                        } else if (kind.equals(SocialNetworkingConstant.REPORT_KIND_ACCOUNT)) {
                            Join<Report, Account> accountJoin = root.join("objectId", JoinType.INNER);
                            predicates.add(cb.equal(accountJoin, objectId));
                        }
                    }
                }
                if (status != null) {
                    predicates.add(cb.equal(root.get("status"), status));
                }
                query.orderBy(cb.asc(root.get("createdDate")));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
