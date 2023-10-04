package com.social.networking.api.model.criteria;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.model.Account;
import com.social.networking.api.model.Bookmark;
import com.social.networking.api.model.Post;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class BookmarkCriteria implements Serializable {
    private Long id;
    private Long accountId;
    private Long postId;
    private Integer status;

    public Specification<Bookmark> getSpecification() {
        return new Specification<Bookmark>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Bookmark> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getAccountId() != null) {
                    Join<Account, Bookmark> accountJoin = root.join("account", JoinType.INNER);
                    predicates.add(cb.equal(accountJoin.get("id"), getAccountId()));
                    predicates.add(cb.equal(accountJoin.get("status"), SocialNetworkingConstant.STATUS_ACTIVE));
                }
                if (getPostId() != null) {
                    Join<Post, Bookmark> postJoin = root.join("post", JoinType.INNER);
                    predicates.add(cb.equal(postJoin.get("id"), getPostId()));
                    predicates.add(cb.equal(postJoin.get("status"), SocialNetworkingConstant.STATUS_ACTIVE));
                }
                if (getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
