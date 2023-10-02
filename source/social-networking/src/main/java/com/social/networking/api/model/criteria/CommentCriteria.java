package com.social.networking.api.model.criteria;

import com.social.networking.api.model.Post;
import com.social.networking.api.model.Comment;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommentCriteria implements Serializable {
    private Long id;
    @NotNull(message = "postId cannot be null!")
    private Long postId;
    private Integer status;
    private Long parentId;

    public Specification<Comment> getSpecification() {
        return new Specification<Comment>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getParentId() != null) {
                    Join<Comment, Comment> commentJoin = root.join("parent", JoinType.INNER);
                    predicates.add(cb.equal(commentJoin.get("id"), getParentId()));
                    query.orderBy(cb.asc(root.get("createdDate")));
                } else {
                    predicates.add(cb.isNull(root.get("parent")));
                    query.orderBy(cb.desc(root.get("createdDate")));
                }
                if (getPostId() != null) {
                    Join<Post, Comment> join = root.join("post", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), getPostId()));
                }
                if (getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
