package com.social.networking.api.model;

import com.social.networking.api.model.audit.Auditable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "permission")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Permission extends Auditable<String> {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.social.networking.api.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    @Column(name = "name", unique = true)
    private String name;
    private String action;
    private Boolean showMenu;
    private String description;
    private String nameGroup;
    @Column(name = "permission_code", unique = true)
    private String permissionCode;
}
