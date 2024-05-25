package com.social.networking.api.model.criteria;

import com.social.networking.api.model.LengthForAge;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Data
public class LengthForAgeCriteria {
    private Long id;
    private Integer gender;
    private Integer age;

    public Specification<LengthForAge> getSpecification() {
        return new Specification<LengthForAge>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<LengthForAge> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getGender() != null) {
                    predicates.add(cb.equal(root.get("gender"), getGender()));
                }
                if (getAge() != null) {
                    predicates.add(cb.equal(root.get("age"), getAge()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
