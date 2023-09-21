package com.social.networking.api.model.criteria;

import com.social.networking.api.model.Group;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class GroupCriteria implements Serializable {
    private Long id;
    private String name;
    private Integer kind;
    private Boolean isSystemRole = false;

    public Specification<Group> getSpecification() {
        return new Specification<Group>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Group> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getName() != null) {
                    predicates.add(cb.like(cb.lower(root.get("name")), "%" + getName().toLowerCase() + "%"));
                }
                if (getKind() != null) {
                    predicates.add(cb.equal(root.get("kind"), getKind()));
                }
                if (getIsSystemRole() != null) {
                    predicates.add(cb.equal(root.get("isSystemRole"), getIsSystemRole()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
