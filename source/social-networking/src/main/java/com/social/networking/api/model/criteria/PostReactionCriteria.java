package com.social.networking.api.model.criteria;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.model.Post;
import com.social.networking.api.model.PostReaction;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostReactionCriteria implements Serializable {
    @NotNull(message = "postId id is required")
    private Long postId;
    private Integer kind;

    public Specification<PostReaction> getSpecification() {
        return new Specification<PostReaction>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<PostReaction> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getPostId() != null) {
                    Join<PostReaction, Post> join = root.join("post", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), getPostId()));
                    predicates.add(cb.equal(join.get("status"), SocialNetworkingConstant.STATUS_ACTIVE));
                }
                if (getKind() != null) {
                    predicates.add(cb.equal(root.get("kind"), getKind()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
