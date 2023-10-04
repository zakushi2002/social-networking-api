package com.social.networking.api.model.criteria;

import com.social.networking.api.model.Permission;
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
public class PermissionCriteria implements Serializable {
    private Long id;
    private String name;
    private String permissionCode;
    private String nameGroup;
    private String action;
    private Boolean showMenu;
    private Integer status;

    public Specification<Permission> getSpecification() {
        return new Specification<Permission>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getName() != null) {
                    predicates.add(cb.like(cb.lower(root.get("name")), "%" + getName().toLowerCase() + "%"));
                }
                if (getPermissionCode() != null) {
                    predicates.add(cb.like(cb.lower(root.get("permissionCode")), "%" + getPermissionCode().toUpperCase() + "%"));
                }
                if (getNameGroup() != null) {
                    predicates.add(cb.like(cb.lower(root.get("nameGroup")), "%" + getNameGroup().toUpperCase() + "%"));
                }
                if (getAction() != null) {
                    predicates.add(cb.like(cb.lower(root.get("action")), "%" + getAction().toUpperCase() + "%"));
                }
                if (getShowMenu() != null) {
                    predicates.add(cb.equal(root.get("showMenu"), getShowMenu()));
                }
                if (getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }
                query.orderBy(cb.desc(root.get("createdDate")));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
