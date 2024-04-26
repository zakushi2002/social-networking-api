package com.social.networking.api.model.criteria;

import com.social.networking.api.model.Category;
import com.social.networking.api.model.Course;
import com.social.networking.api.model.ExpertProfile;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CourseCriteria implements Serializable {
    private Long id;
    private String title;
    private Long expertId;
    private Long fromDate;
    private Long toDate;
    private Long topicId;
    public Specification<Course> getSpecification() {
        return new Specification<Course>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getExpertId() != null) {
                    Join<ExpertProfile, Course> expertJoin = root.join("expert", JoinType.INNER);
                    predicates.add(cb.equal(expertJoin.get("id"), getExpertId()));
                }
                if (getTopicId() != null) {
                    Join<Category, Course> topicJoin = root.join("topic", JoinType.INNER);
                    predicates.add(cb.equal(topicJoin.get("id"), getTopicId()));
                }
                if (getTitle() != null) {
                    predicates.add(cb.like(cb.lower(root.get("title")), "%" + getTitle().toLowerCase() + "%"));
                }
                if (getFromDate() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), new Date(getFromDate())));
                }
                if (getToDate() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("startDate"), new Date(getToDate())));
                }
                query.orderBy(cb.desc(root.get("startDate")));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
