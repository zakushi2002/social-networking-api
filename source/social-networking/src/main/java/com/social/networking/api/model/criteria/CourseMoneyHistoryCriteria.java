package com.social.networking.api.model.criteria;

import com.social.networking.api.model.CourseMoneyHistory;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Data
public class CourseMoneyHistoryCriteria {
    private Long courseId;
    private String bankName;
    private String accountNumber;

    public Specification<CourseMoneyHistory> getSpecification() {
        return new Specification<CourseMoneyHistory>() {
            @Override
            public Predicate toPredicate(Root<CourseMoneyHistory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (courseId != null) {
                    predicates.add(cb.equal(root.get("courseRequest").get("course").get("id"), courseId));
                }
                if (bankName != null) {
                    predicates.add(cb.like(cb.lower(root.get("bankName")), "%" + bankName.toLowerCase() + "%"));
                }
                if (accountNumber != null) {
                    predicates.add(cb.equal(root.get("accountNumber"), accountNumber));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
