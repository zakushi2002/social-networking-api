package com.social.networking.api.model.criteria;

import com.social.networking.api.model.Account;
import com.social.networking.api.model.Category;
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
    private Long hospitalId;
    private Long hospitalRoleId;
    private Long academicDegreeId;
    private Long departmentId;
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
                if (getHospitalId() != null) {
                    Join<ExpertProfile, Category> hospital = root.join("hospital", JoinType.INNER);
                    predicates.add(cb.equal(hospital.get("id"), getHospitalId()));
                }
                if (getHospitalRoleId() != null) {
                    Join<ExpertProfile, Category> hospitalRole = root.join("hospitalRole", JoinType.INNER);
                    predicates.add(cb.equal(hospitalRole.get("id"), getHospitalRoleId()));
                }
                if (getAcademicDegreeId() != null) {
                    Join<ExpertProfile, Category> academicDegree = root.join("academicDegree", JoinType.INNER);
                    predicates.add(cb.equal(academicDegree.get("id"), getAcademicDegreeId()));
                }
                if (getDepartmentId() != null) {
                    Join<ExpertProfile, Category> department = root.join("department", JoinType.INNER);
                    predicates.add(cb.equal(department.get("id"), getDepartmentId()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
