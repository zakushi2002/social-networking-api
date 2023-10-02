package com.social.networking.api.model.criteria;

import com.social.networking.api.model.Comment;
import com.social.networking.api.model.CommentReaction;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommentReactionCriteria implements Serializable {
    @NotNull(message = "commentId id is required")
    private Long commentId;
    private Integer kind;

    public Specification<CommentReaction> getSpecification() {
        return new Specification<CommentReaction>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<CommentReaction> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getCommentId() != null) {
                    Join<CommentReaction, Comment> join = root.join("comment", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), getCommentId()));
                }
                if (getKind() != null) {
                    predicates.add(cb.equal(root.get("kind"), getKind()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
