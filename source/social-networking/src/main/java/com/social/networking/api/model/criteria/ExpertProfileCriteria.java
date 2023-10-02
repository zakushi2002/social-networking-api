package com.social.networking.api.model.criteria;

import com.social.networking.api.model.Account;
import com.social.networking.api.model.ExpertProfile;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ExpertProfileCriteria implements Serializable {
    private Long id;
    private String email;
    private String fullName;
    private String phone;
    private Integer status;

    public Specification<ExpertProfile> getSpecification() {
        return new Specification<ExpertProfile>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<ExpertProfile> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                Join<ExpertProfile, Account> account = root.join("account", JoinType.INNER);
                if (getStatus() != null) {
                    predicates.add(cb.equal(account.get("status"), getStatus()));
                }
                if (getEmail() != null) {
                    predicates.add(cb.like(cb.lower(account.get("email")), "%" + getEmail().toLowerCase() + "%"));
                }
                if (getFullName() != null) {
                    predicates.add(cb.like(cb.lower(account.get("fullName")), "%" + getFullName().toLowerCase() + "%"));
                }
                if (getPhone() != null) {
                    predicates.add(cb.like(cb.lower(root.get("phone")), "%" + getPhone().toLowerCase() + "%"));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
