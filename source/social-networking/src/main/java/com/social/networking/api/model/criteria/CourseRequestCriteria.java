package com.social.networking.api.model.criteria;

import com.social.networking.api.model.CourseRequest;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Data
public class CourseRequestCriteria {
    private Long courseId;
    private String courseName;
    private String fullName;
    private String email;
    private String phone;

    public Specification<CourseRequest> getSpecification() {
        return new Specification<CourseRequest>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<CourseRequest> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (getCourseId() != null) {
                    predicates.add(cb.equal(root.get("course").get("id"), getCourseId()));
                }
                if (getCourseName() != null) {
                    predicates.add(cb.like(cb.lower(root.get("course").get("title")), "%" + getCourseName().toLowerCase() + "%"));
                }
                if (getFullName() != null) {
                    predicates.add(cb.like(cb.lower(root.get("fullName")), "%" + getFullName().toLowerCase() + "%"));
                }
                if (getEmail() != null) {
                    predicates.add(cb.like(cb.lower(root.get("email")), "%" + getEmail().toLowerCase() + "%"));
                }
                if (getPhone() != null) {
                    predicates.add(cb.like(cb.lower(root.get("phone")), "%" + getPhone().toLowerCase() + "%"));
                }
                query.orderBy(cb.desc(root.get("createdDate")));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
